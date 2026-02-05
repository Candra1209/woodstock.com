package com.woodstock.app.utils.tool;

import jakarta.servlet.http.HttpServletRequest;

public class UrlBuilderHelper {

    public static String getFullUrl(HttpServletRequest request){

        String query = request.getQueryString() != null ? "?" + request.getQueryString() : "";

        return request.getRequestURL() + query;
    }

}
