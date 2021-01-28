package com.alexqzhang.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DataProvider extends ContentProvider {
    public static final String AUTHORITIES = "com.alexqzhang.data";
    public static final String COURSE_PATH = "course";


    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITIES);
    public static final Uri COURSE_URI = Uri.withAppendedPath(BASE_URI, COURSE_PATH);

    public DataProvider() {
        super();
    }

    @Override
    public void onCallingPackageChanged() {
        super.onCallingPackageChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder, @Nullable CancellationSignal cancellationSignal) {
        return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable Bundle queryArgs, @Nullable CancellationSignal cancellationSignal) {
        return super.query(uri, projection, queryArgs, cancellationSignal);
    }

    @Nullable
    @Override
    public Uri canonicalize(@NonNull Uri url) {
        return super.canonicalize(url);
    }

    @Nullable
    @Override
    public Uri uncanonicalize(@NonNull Uri url) {
        return super.uncanonicalize(url);
    }

    @Override
    public boolean refresh(Uri uri, @Nullable Bundle extras, @Nullable CancellationSignal cancellationSignal) {
        return super.refresh(uri, extras, cancellationSignal);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values, @Nullable Bundle extras) {
        return super.insert(uri, values, extras);
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable Bundle extras) {
        return super.delete(uri, extras);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable Bundle extras) {
        return super.update(uri, values, extras);
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        return super.openFile(uri, mode);
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode, @Nullable CancellationSignal signal) throws FileNotFoundException {
        return super.openFile(uri, mode, signal);
    }

    @Nullable
    @Override
    public AssetFileDescriptor openAssetFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        return super.openAssetFile(uri, mode);
    }

    @Nullable
    @Override
    public AssetFileDescriptor openAssetFile(@NonNull Uri uri, @NonNull String mode, @Nullable CancellationSignal signal) throws FileNotFoundException {
        return super.openAssetFile(uri, mode, signal);
    }

    @Nullable
    @Override
    public String[] getStreamTypes(@NonNull Uri uri, @NonNull String mimeTypeFilter) {
        return super.getStreamTypes(uri, mimeTypeFilter);
    }

    @Nullable
    @Override
    public AssetFileDescriptor openTypedAssetFile(@NonNull Uri uri, @NonNull String mimeTypeFilter, @Nullable Bundle opts) throws FileNotFoundException {
        return super.openTypedAssetFile(uri, mimeTypeFilter, opts);
    }

    @Nullable
    @Override
    public AssetFileDescriptor openTypedAssetFile(@NonNull Uri uri, @NonNull String mimeTypeFilter, @Nullable Bundle opts, @Nullable CancellationSignal signal) throws FileNotFoundException {
        return super.openTypedAssetFile(uri, mimeTypeFilter, opts, signal);
    }

    @NonNull
    @Override
    public <T> ParcelFileDescriptor openPipeHelper(@NonNull Uri uri, @NonNull String mimeType, @Nullable Bundle opts, @Nullable T args, @NonNull PipeDataWriter<T> func) throws FileNotFoundException {
        return super.openPipeHelper(uri, mimeType, opts, args, func);
    }

    @Override
    protected boolean isTemporary() {
        return super.isTemporary();
    }

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        super.attachInfo(context, info);
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull String authority, @NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        return super.applyBatch(authority, operations);
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        return super.applyBatch(operations);
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String authority, @NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        return super.call(authority, method, arg, extras);
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        return super.call(method, arg, extras);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
