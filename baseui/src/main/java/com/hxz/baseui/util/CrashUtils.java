package com.hxz.baseui.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : utils about crash
 * </pre>
 */
public final class CrashUtils {

    private static final String FILE_SEP = System.getProperty("file.separator");

    private static final UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

    private CrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Initialization.
     */
    @SuppressLint("MissingPermission")
    public static void init() {
        init("");
    }

    /**
     * Initialization
     *
     * @param crashDir The directory of saving crash information.
     */
    public static void init(@NonNull final File crashDir) {
        init(crashDir.getAbsolutePath(), null);
    }

    /**
     * Initialization
     *
     * @param crashDirPath The directory's path of saving crash information.
     */
    public static void init(final String crashDirPath) {
        init(crashDirPath, null);
    }

    /**
     * Initialization
     *
     * @param onCrashListener The crash listener.
     */
    public static void init(final OnCrashListener onCrashListener) {
        init("", onCrashListener);
    }

    /**
     * Initialization
     *
     * @param crashDir        The directory of saving crash information.
     * @param onCrashListener The crash listener.
     */
    public static void init(@NonNull final File crashDir, final OnCrashListener onCrashListener) {
        init(crashDir.getAbsolutePath(), onCrashListener);
    }

    /**
     * Initialization
     *
     * @param crashDirPath    The directory's path of saving crash information.
     * @param onCrashListener The crash listener.
     */
    public static void init(final String crashDirPath, final OnCrashListener onCrashListener) {
        String dirPath;
        if (TextUtils.isEmpty(crashDirPath)) {
            if (FileUtils.INSTANCE.isSDCardEnableByEnvironment()
                    && AppUtils.INSTANCE.getApp().getExternalFilesDir(null) != null)
                dirPath = AppUtils.INSTANCE.getApp().getExternalFilesDir(null) + FILE_SEP + "crash" + FILE_SEP;
            else {
                dirPath = AppUtils.INSTANCE.getApp().getFilesDir() + FILE_SEP + "crash" + FILE_SEP;
            }
        } else {
            dirPath = crashDirPath.endsWith(FILE_SEP) ? crashDirPath : crashDirPath + FILE_SEP;
        }
        Thread.setDefaultUncaughtExceptionHandler(getUncaughtExceptionHandler(dirPath, onCrashListener));
    }

    private static UncaughtExceptionHandler getUncaughtExceptionHandler(final String dirPath,
                                                                        final OnCrashListener onCrashListener) {
        return new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull final Thread t, @NonNull final Throwable e) {
                final String time = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
                final StringBuilder sb = new StringBuilder();
                final String head = "************* Log Head ****************" +
                        "\nTime Of Crash      : " + time +
                        "\nDevice Manufacturer: " + Build.MANUFACTURER +
                        "\nDevice Model       : " + Build.MODEL +
                        "\nAndroid Version    : " + Build.VERSION.RELEASE +
                        "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                        "\nApp VersionName    : " + AppUtils.INSTANCE.getAppVersionName() +
                        "\nApp VersionCode    : " + AppUtils.INSTANCE.getAppVersionCode() +
                        "\n************* Log Head ****************\n\n";
                sb.append(head).append(getFullStackTrace(e));
                final String crashInfo = sb.toString();
                final String crashFile = dirPath + time + ".txt";
                FileUtils.INSTANCE.writeFileFromString(crashFile, crashInfo, true);

                if (onCrashListener != null) {
                    onCrashListener.onCrash(crashInfo, e);
                }

                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
                }
            }
        };
    }

    private static final String LINE_SEP = System.getProperty("line.separator");

    public static String getFullStackTrace(Throwable throwable) {
        final List<Throwable> throwableList = new ArrayList<>();
        while (throwable != null && !throwableList.contains(throwable)) {
            throwableList.add(throwable);
            throwable = throwable.getCause();
        }
        final int size = throwableList.size();
        final List<String> frames = new ArrayList<>();
        List<String> nextTrace = getStackFrameList(throwableList.get(size - 1));
        for (int i = size; --i >= 0; ) {
            final List<String> trace = nextTrace;
            if (i != 0) {
                nextTrace = getStackFrameList(throwableList.get(i - 1));
                removeCommonFrames(trace, nextTrace);
            }
            if (i == size - 1) {
                frames.add(throwableList.get(i).toString());
            } else {
                frames.add(" Caused by: " + throwableList.get(i).toString());
            }
            frames.addAll(trace);
        }
        StringBuilder sb = new StringBuilder();
        for (final String element : frames) {
            sb.append(element).append(LINE_SEP);
        }
        return sb.toString();
    }

    private static List<String> getStackFrameList(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        final String stackTrace = sw.toString();
        final StringTokenizer frames = new StringTokenizer(stackTrace, LINE_SEP);
        final List<String> list = new ArrayList<>();
        boolean traceStarted = false;
        while (frames.hasMoreTokens()) {
            final String token = frames.nextToken();
            // Determine if the line starts with <whitespace>at
            final int at = token.indexOf("at");
            if (at != -1 && token.substring(0, at).trim().isEmpty()) {
                traceStarted = true;
                list.add(token);
            } else if (traceStarted) {
                break;
            }
        }
        return list;
    }

    private static void removeCommonFrames(final List<String> causeFrames, final List<String> wrapperFrames) {
        int causeFrameIndex = causeFrames.size() - 1;
        int wrapperFrameIndex = wrapperFrames.size() - 1;
        while (causeFrameIndex >= 0 && wrapperFrameIndex >= 0) {
            // Remove the frame from the cause trace if it is the same
            // as in the wrapper trace
            final String causeFrame = causeFrames.get(causeFrameIndex);
            final String wrapperFrame = wrapperFrames.get(wrapperFrameIndex);
            if (causeFrame.equals(wrapperFrame)) {
                causeFrames.remove(causeFrameIndex);
            }
            causeFrameIndex--;
            wrapperFrameIndex--;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface OnCrashListener {
        void onCrash(String crashInfo, Throwable e);
    }
}
