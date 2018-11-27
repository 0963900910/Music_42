package com.framgia.quangtran.music_42.util;

import com.framgia.quangtran.music_42.BuildConfig;
import com.framgia.quangtran.music_42.data.model.GenreKey;

public class StringUtil {
    public static final String CLIENT_ID = "&client_id=";
    public static final String BASE_URL_GENRES = "https://api-v2.soundcloud.com/charts?kind=top&genre=";
    public static final String BASE_URL_TRACK = "http://api.soundcloud.com/tracks";
    public static final String PARAMETER_LIMIT = "&limit=";
    public static final String PARAMETER_OFFSET = "&offset=";
    public static final String PARAMETER_SEARCH = "&q=";
    public static final String PARAMETER_ID = "?client_id=";
    public static final String NAME_STREAM = "stream";
    public static final String SPLASH = "/";
    public static final String QUESTION_MARK = "?";
    public static final String EXTRA_TRACK = "track";
    public static final String EXTRA_GENRES = "genres";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_PROGRESS = "progress";

    public static String append(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            builder.append(string);
        }
        return builder.toString();
    }

    public static String genreApi(@GenreKey String genreKey, int limit, int offset) {
        return StringUtil.append(BASE_URL_GENRES, genreKey,
                CLIENT_ID, BuildConfig.API_KEY,
                PARAMETER_LIMIT, String.valueOf(limit),
                PARAMETER_OFFSET, String.valueOf(offset));
    }

    public static String initStreamApi(long trackId) {
        return StringUtil.append(BASE_URL_TRACK, SPLASH,
                String.valueOf(trackId), SPLASH,
                NAME_STREAM, QUESTION_MARK,
                CLIENT_ID, BuildConfig.API_KEY);
    }
}
