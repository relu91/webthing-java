package io.webthings.webthing.server.securityHandlers;

import fi.iki.elonen.NanoHTTPD;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.server.SecurityHandler;
import io.webthings.webthing.server.common.Hex;
import io.webthings.webthing.server.securityHandlers.exceptions.InvalidSecurityException;
import io.webthings.webthing.server.securityHandlers.exceptions.RequireAuthenticationException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Lorenzo
 */
public class DigestSecurityHandler extends SecurityHandler {
    private static String realm = "test@test.com";
    private static String nonce = "dcd98b7102dd2f0e8b11d0f600bfb0c093";
    private static String opaque = "5ccc069c403ebaf9f0171e9517f40e41";
    private static String pwd = "pwd";
    private static final String DIGEST_MARKER = "Digest";

    public static void init(String realm,
                            String pwd,
                            String opaque,
                            String nonce) {
        DigestSecurityHandler.realm = realm;
        DigestSecurityHandler.pwd = pwd;
        DigestSecurityHandler.opaque = opaque;
        DigestSecurityHandler.nonce = nonce;
    }

    @Override
    public boolean doSecurityCheck(SecurityScheme sc,
                                   NanoHTTPD.IHTTPSession session)
            throws WoTException {
        final String authHeader = findHeader("Authorization", session);
        if (authHeader == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Digest");
            sb.append(" ");
            sb.append("realm=\"").append(realm).append("\",");
            sb.append("qop=")
              .append("\"")
              .append("auth,auth-int")
              .append("\",");
            sb.append("nonce=").append("\"").append(nonce).append("\",");
            sb.append("opaque=").append("\"").append(opaque).append("\"");

            throw new RequireAuthenticationException(sb.toString());
        }

        boolean ret = false;

        final int pos = authHeader.indexOf(DIGEST_MARKER);
        final int startAuthPos = pos + DIGEST_MARKER.length();

        final String authString = authHeader.substring(startAuthPos).trim();


        final String sAuthOpts[] = authString.split(",");
        final TreeMap<String, String> authOpts = new TreeMap<>();
        for (final String s : sAuthOpts) {
            final String keyValue[] = s.split("\\=");
            if (keyValue.length == 2) {
                authOpts.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        //check qop
        final String qop = getRequiredSecurityHeader(authOpts, "nop");
        if (qop.equals("auth") == false && qop.equals("auth-int") == false) {
            throw new InvalidSecurityException("qop : " + qop);
        }

        try {
            final String username =
                    getRequiredSecurityHeader(authOpts, "username");
            final String realm = getRequiredSecurityHeader(authOpts, "realm");
            final String response =
                    getRequiredSecurityHeader(authOpts, "response");
            final String cnonce = getRequiredSecurityHeader(authOpts, "cnonce");
            final String nc = getRequiredSecurityHeader(authOpts, "nc");
            final String nonce = getRequiredSecurityHeader(authOpts, "nonce");
            final String method = session.getMethod().name();
            final String uri = getRequiredSecurityHeader(authOpts, "uri");
            final String body = parseBody(session);
            final String opaque = getRequiredSecurityHeader(authOpts, "opaque");

            ret = checkDigest(username,
                              realm,
                              response,
                              nonce,
                              nc,
                              cnonce,
                              qop,
                              method,
                              uri,
                              body,
                              opaque);

            return ret;
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidSecurityException("No MD5");
        }
    }

    private String getRequiredSecurityHeader(Map<String, String> m, String k)
            throws InvalidSecurityException {
        if (m == null) {
            throw new InvalidSecurityException("security map");
        }
        final String ret = m.get(k);
        if (ret == null || ret.length() == 0) {
            throw new InvalidSecurityException(k);
        }

        return ret;
    }

    private static String computeHA1(String u, String r, String p)
            throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final StringBuilder sb = new StringBuilder();
        sb.append(u).append(":").append(r).append(":").append(p);

        return Hex.bytesToHex(md.digest(sb.toString().getBytes()));
    }

    public static boolean checkDigest(String username,
                                      String realm,
                                      String response,
                                      String nonce,
                                      String nc,
                                      String cnonce,
                                      String qop,
                                      String method,
                                      String uri,
                                      String body,
                                      String opaque)
            throws InvalidSecurityException, NoSuchAlgorithmException {
        if (nonce.equals(DigestSecurityHandler.nonce) == false) {
            throw new InvalidSecurityException("Different nonce");
        }

        if (opaque.equals(DigestSecurityHandler.opaque) == false) {
            throw new InvalidSecurityException("Different opaque");
        }


        final String ha1 = computeHA1(username, realm, pwd);
        String ha2 = null;


        if (qop.equals("auth")) {
            final StringBuilder sb = new StringBuilder();
            sb.append(method).append(":").append(uri);
            final MessageDigest md = MessageDigest.getInstance("MD5");
            ha2 = Hex.bytesToHex(md.digest(sb.toString().getBytes()));
        }

        if (qop.equals("auth-int")) {
            final StringBuilder sb = new StringBuilder();

            final MessageDigest md = MessageDigest.getInstance("MD5");
            final String md5Body = Hex.bytesToHex(md.digest(body.getBytes()));
            sb.append(method)
              .append(":")
              .append(uri)
              .append(":")
              .append(md5Body);

            md.reset();
            ha2 = Hex.bytesToHex(md.digest(sb.toString().getBytes()));
        }

        if (ha2 == null) {
            throw new InvalidSecurityException("No HA2");
        }

        final MessageDigest md = MessageDigest.getInstance("MD5");
        final StringBuilder sb = new StringBuilder();
        sb.append(ha1)
          .append(":")
          .append(DigestSecurityHandler.nonce)
          .append(":")
          .append(nc)
          .append(":")
          .append(cnonce)
          .append(":")
          .append(qop)
          .append(":")
          .append(ha2);

        final String computedResult =
                Hex.bytesToHex(md.digest(sb.toString().getBytes()));

        final boolean ret = response.equals(computedResult);
        return ret;
    }

    private static String parseBody(NanoHTTPD.IHTTPSession session) {
        int contentLength =
                Integer.parseInt(session.getHeaders().get("content-length"));
        byte[] buffer = new byte[contentLength];
        try {
            session.getInputStream().read(buffer, 0, contentLength);
            return new String(buffer);
        } catch (IOException e) {
            return null;
        }
    }
}
