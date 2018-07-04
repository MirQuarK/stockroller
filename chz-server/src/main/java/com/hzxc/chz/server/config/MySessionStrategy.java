package com.hzxc.chz.server.config;

import org.springframework.session.Session;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Pattern;

public class MySessionStrategy implements HttpSessionStrategy{

    private String headerName = "x-auth-token";

    private static final String DEFAULT_DELIMITER = " ";
    private static final String SESSION_IDS_WRITTEN_ATTR = CookieHttpSessionStrategy.class.getName().concat(".SESSIONS_WRITTEN_ATTR");
    static final String DEFAULT_ALIAS = "0";
    static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_s";
    private static final Pattern ALIAS_PATTERN = Pattern.compile("^[\\w-]{1,50}$");
    private String sessionParam = "_s";
    private CookieSerializer cookieSerializer = new DefaultCookieSerializer();
    private String deserializationDelimiter = " ";
    private String serializationDelimiter = " ";

    @Override
    public String getRequestedSessionId (HttpServletRequest request) {
        String id = request.getHeader(this.headerName);
        if(id == null) {
            Map<String, String> sessionIds = this.getSessionIds(request);
            String sessionAlias = this.getCurrentSessionAlias(request);
            return (String)sessionIds.get(sessionAlias);
        }
        return id;
    }

    @Override
    public void onNewSession (Session session, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.headerName, session.getId());

        Set<String> sessionIdsWritten = this.getSessionIdsWritten(request);
        if (!sessionIdsWritten.contains(session.getId())) {
            sessionIdsWritten.add(session.getId());
            Map<String, String> sessionIds = this.getSessionIds(request);
            String sessionAlias = this.getCurrentSessionAlias(request);
            sessionIds.put(sessionAlias, session.getId());
            String cookieValue = this.createSessionCookieValue(sessionIds);
            this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, cookieValue));
        }
    }

    @Override
    public void onInvalidateSession (HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.headerName, "");

        Map<String, String> sessionIds = this.getSessionIds(request);
        String requestedAlias = this.getCurrentSessionAlias(request);
        sessionIds.remove(requestedAlias);
        String cookieValue = this.createSessionCookieValue(sessionIds);
        this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, cookieValue));
    }

    public void setHeaderName(String headerName) {
        Assert.notNull(headerName, "headerName cannot be null");
        this.headerName = headerName;
    }

    public Map<String, String> getSessionIds(HttpServletRequest request) {
        List<String> cookieValues = this.cookieSerializer.readCookieValues(request);
        String sessionCookieValue = cookieValues.isEmpty() ? "" : (String)cookieValues.iterator().next();
        Map<String, String> result = new LinkedHashMap();
        StringTokenizer tokens = new StringTokenizer(sessionCookieValue, this.deserializationDelimiter);
        if (tokens.countTokens() == 1) {
            result.put("0", tokens.nextToken());
            return result;
        } else {
            while(tokens.hasMoreTokens()) {
                String alias = tokens.nextToken();
                if (!tokens.hasMoreTokens()) {
                    break;
                }

                String id = tokens.nextToken();
                result.put(alias, id);
            }

            return result;
        }
    }

    public String getCurrentSessionAlias(HttpServletRequest request) {
        if (this.sessionParam == null) {
            return "0";
        } else {
            String u = request.getParameter(this.sessionParam);
            if (u == null) {
                return "0";
            } else {
                return !ALIAS_PATTERN.matcher(u).matches() ? "0" : u;
            }
        }
    }

    private String createSessionCookieValue(Map<String, String> sessionIds) {
        if (sessionIds.isEmpty()) {
            return "";
        } else if (sessionIds.size() == 1 && sessionIds.keySet().contains("0")) {
            return (String)sessionIds.values().iterator().next();
        } else {
            StringBuffer buffer = new StringBuffer();
            Iterator var3 = sessionIds.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var3.next();
                String alias = (String)entry.getKey();
                String id = (String)entry.getValue();
                buffer.append(alias);
                buffer.append(this.serializationDelimiter);
                buffer.append(id);
                buffer.append(this.serializationDelimiter);
            }

            buffer.deleteCharAt(buffer.length() - 1);
            return buffer.toString();
        }
    }

    private Set<String> getSessionIdsWritten(HttpServletRequest request) {
        Set<String> sessionsWritten = (Set)request.getAttribute(SESSION_IDS_WRITTEN_ATTR);
        if (sessionsWritten == null) {
            sessionsWritten = new HashSet();
            request.setAttribute(SESSION_IDS_WRITTEN_ATTR, sessionsWritten);
        }

        return (Set)sessionsWritten;
    }
}
