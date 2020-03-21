package com.ahmednafe3.photoweather.base.status;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StatusResponse<T> {
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String error;

    private StatusResponse(Status status, @Nullable T data, @Nullable String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> StatusResponse<T> loading() {
        return new StatusResponse<>(Status.LOADING, null, null);
    }

    public static <T> StatusResponse<T> progress(@NonNull T data) {

        return new StatusResponse<>(Status.PROGRESS, data, null);

    }

    public static <T> StatusResponse<T> success(@NonNull T data) {

        return new StatusResponse<>(Status.SUCCESS, data, null);

    }

    /*use complete if you do not need data again*/
    public static <T> StatusResponse<T> complete() {
        return new StatusResponse<>(Status.COMPLETE, null, null);
    }

    public static <T> StatusResponse<T> error(@NonNull String error) {

        return new StatusResponse<>(Status.ERROR, null, error);

    }
}
