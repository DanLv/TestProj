/* Copyright 2015 Huami Inc. All rights reserved. */
package me.dan.test.danlib.me.dan.test.danlib.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * @author DanLv
 */
public class Log {
    private static final Settings settings = new Settings();
    private static final Printer printer = new LoggerPrinter(settings);
    private static final Printer filePrinter = new FileLoggerPrinter(settings);
    private static final String DEFAULT_TAG = "LOGGER";

    private Log() {
    }

    public static Settings init() {
        printer.init(DEFAULT_TAG);
        return settings;
    }

    public static Settings init(String tag) {
        printer.init(tag);
        return settings;
    }

    public static Printer t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static Printer t(int methodCount) {
        return printer.t(null, methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void d(String tag, String message, Object... args) {
        //t(tag);
        printer.d(tag, message, args);
        if (settings.isLogToFile()) {
            filePrinter.d(tag, message, args);
        }
    }

    public static void e(String tag, String message, Object... args) {
        //t(tag);
        printer.e(tag, message, args);
        if (settings.isLogToFile()) {
            filePrinter.e(tag, message, args);
        }
    }

    public static void e(String tag, String message, Throwable throwable, Object... args) {
        //t(tag);
        printer.e(tag, throwable, message, args);
        if (settings.isLogToFile()) {
            filePrinter.e(tag, throwable, message, args);
        }
    }

    public static void i(String tag, String message, Object... args) {
        //t(tag);
        printer.i(tag, message, args);
        if (settings.isLogToFile()) {
            filePrinter.i(tag, message, args);
        }
    }

    public static void v(String tag, String message, Object... args) {
        //t(tag);
        printer.v(tag, message, args);
        if (settings.isLogToFile()) {
            filePrinter.v(tag, message, args);
        }
    }

    public static void w(String tag, String message, Object... args) {
        //t(tag);
        printer.w(tag, message, args);
        if (settings.isLogToFile()) {
            filePrinter.w(tag, message, args);
        }
    }

    public static void w(String tag, String message, Throwable throwable, Object... args) {
        //t(tag);
        printer.w(tag, throwable, message, args);
        if (settings.isLogToFile()) {
            filePrinter.w(tag, throwable, message, args);
        }
    }

    public static void wtf(String tag, String message, Object... args) {
        //t(tag);
        printer.wtf(tag, message, args);
        if (settings.isLogToFile()) {
            filePrinter.wtf(tag, message, args);
        }
    }

    public static void json(String tag, String json) {
        //t(tag);
        printer.json(tag, json);
        if (settings.isLogToFile()) {
            filePrinter.json(tag, json);
        }
    }

    public static void xml(String tag, String xml) {
        //t(tag);
        printer.xml(tag, xml);
        if (settings.isLogToFile()) {
            filePrinter.xml(tag, xml);
        }
    }

    private interface Printer {
        Printer t(String tag, int methodCount);

        Settings init(String tag);

        Settings getSettings();

        void d(String tag, String message, Object... args);

        void e(String tag, String message, Object... args);

        void e(String tag, Throwable throwable, String message, Object... args);

        void w(String tag, String message, Object... args);

        void w(String tag, Throwable throwable, String message, Object... args);

        void i(String tag, String message, Object... args);

        void v(String tag, String message, Object... args);

        void wtf(String tag, String message, Object... args);

        void json(String tag, String json);

        void xml(String tag, String xml);
    }

    private static class LoggerPrinter implements Printer {
        private static final int CHUNK_SIZE = 4000;

        private static final int JSON_INDENT = 4;

        private static final int MIN_STACK_OFFSET = 3;

        protected Settings settings = new Settings();

        //private static final char TOP_LEFT_CORNER = '╔';
        //private static final char BOTTOM_LEFT_CORNER = '╚';
        //private static final char MIDDLE_CORNER = '╟';
        //private static final char HORIZONTAL_DOUBLE_LINE = '║';
        //private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
        private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
        private static final String TOP_BORDER = SINGLE_DIVIDER + SINGLE_DIVIDER;
        //private static final String BOTTOM_BORDER = SINGLE_DIVIDER + SINGLE_DIVIDER;
        private static final String MIDDLE_BORDER = " " + SINGLE_DIVIDER + SINGLE_DIVIDER;

        private static String TAG = "LOGGER";

        private static final ThreadLocal<String> LOCAL_TAG = new ThreadLocal<>();
        private static final ThreadLocal<Integer> LOCAL_METHOD_COUNT = new ThreadLocal<>();

        public LoggerPrinter(Settings settings) {
            this.settings = settings;
        }

        @Override
        public Settings init(String tag) {
            if (tag == null) {
                throw new NullPointerException("tag may not be null");
            }
            if (tag.trim().length() == 0) {
                throw new IllegalStateException("tag may not be empty");
            }
            LoggerPrinter.TAG = tag;
            return settings;
        }

        @Override
        public Settings getSettings() {
            return settings;
        }

        @Override
        public Printer t(String tag, int methodCount) {
            if (tag != null) {
                LOCAL_TAG.set(tag);
            }
            LOCAL_METHOD_COUNT.set(methodCount);
            return this;
        }

        @Override
        public void d(String tag, String message, Object... args) {
            log(android.util.Log.DEBUG, tag, message, args);
        }

        @Override
        public void e(String tag, String message, Object... args) {
            e(tag, null, message, args);
        }

        @Override
        public void e(String tag, Throwable throwable, String message, Object... args) {
            logThrowable(android.util.Log.ERROR, throwable, tag, message, args);
        }

        private void logThrowable(int logType, Throwable throwable, String messageTag, String
                message, Object[] args) {
            if (throwable != null && message != null) {
                message += '\n' + android.util.Log.getStackTraceString(throwable);
            }
            if (throwable != null && message == null) {
                message = android.util.Log.getStackTraceString(throwable);
            }
            if (message == null) {
                message = "No message/exception is set";
            }
            log(logType, messageTag, message, args);
        }

        @Override
        public void w(String tag, String message, Object... args) {
            w(tag, null, message, args);
        }

        @Override
        public void w(String tag, Throwable throwable, String message, Object... args) {
            logThrowable(android.util.Log.WARN, throwable, tag, message, args);
        }

        @Override
        public void i(String tag, String message, Object... args) {
            log(android.util.Log.INFO, tag, message, args);
        }

        @Override
        public void v(String tag, String message, Object... args) {
            log(android.util.Log.VERBOSE, tag, message, args);
        }

        @Override
        public void wtf(String tag, String message, Object... args) {
            log(android.util.Log.ASSERT, tag, message, args);
        }

        @Override
        public void json(String tag, String json) {
            if (TextUtils.isEmpty(json)) {
                d(tag, "Empty/Null json content");
                return;
            }
            try {
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    String message = jsonObject.toString(JSON_INDENT);
                    d(tag, message);
                    return;
                }
                if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    String message = jsonArray.toString(JSON_INDENT);
                    d(tag, message);
                }
            } catch (JSONException e) {
                e(tag, e.getCause().getMessage() + "\n" + json);
            }
        }

        @Override
        public void xml(String tag, String xml) {
            if (TextUtils.isEmpty(xml)) {
                d(tag, "Empty/Null xml content");
                return;
            }
            try {
                Source xmlInput = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(xmlInput, xmlOutput);
                d(tag, xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
            } catch (TransformerException e) {
                e(tag, e.getCause().getMessage() + "\n" + xml);
            }
        }

        protected boolean needLog() {
            return settings.getLogLevel() != LogLevel.NONE;
        }

        private synchronized void log(int logType, String messageTag, String msg, Object... args) {
            if (!needLog()) {
                return;
            }
            String tag = getTag();
            String message = createMessage(msg, args);
            int methodCount = getMethodCount();

            logTopBorder(logType, tag);
            logHeaderContent(logType, tag, methodCount);

            byte[] bytes = message.getBytes();
            int length = bytes.length;
            String formattedMessageTag = "[" + messageTag + "] ";
            if (length <= CHUNK_SIZE) {
                if (methodCount > 0) {
                    logDivider(logType, tag);
                }
                logContent(logType, tag, formattedMessageTag + message);
                //logBottomBorder(logType, tag);
                return;
            }
            if (methodCount > 0) {
                logDivider(logType, tag);
            }
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int count = Math.min(length - i, CHUNK_SIZE);
                logContent(logType, tag, formattedMessageTag + new String(bytes, i, count));
            }
            logBottomBorder(logType, tag);
        }

        private void logTopBorder(int logType, String tag) {
            logChunk(logType, tag, TOP_BORDER);
        }

        private void logHeaderContent(int logType, String tag, int methodCount) {
            StackTraceElement[] trace = Thread.currentThread().getStackTrace();
            //if (settings.isShowThreadInfo()) {
            //    logChunk(logType, tag, "Thread : " + Thread.currentThread().getName());
            //    logDivider(logType, tag);
            //}
            String level = "";

            int stackOffset = getStackOffset(trace) + settings.getMethodOffset();

            if (methodCount + stackOffset > trace.length) {
                methodCount = trace.length - stackOffset - 1;
            }

            StringBuilder builder = new StringBuilder();
            for (int i = methodCount; i > 0; i--) {
                int stackIndex = i + stackOffset;
                if (stackIndex >= trace.length) {
                    continue;
                }

                if (settings.isShowThreadInfo() && i == methodCount) {
                    builder.append("(Thread : ")
                            .append(Thread.currentThread().getName())
                            .append(")-");
                }

                String className = getSimpleClassName(trace[stackIndex].getClassName());
                builder.append(level)
                        .append(className)
                        .append(".")
                        .append(trace[stackIndex].getMethodName())
                        //.append(" ")
                        .append("(");

                //String fileName = trace[stackIndex].getFileName();
                //if (!fileName.substring(0, fileName.length() - 5).equals(className)) {
                //    builder.append(trace[stackIndex].getFileName())
                //            .append(":");
                //}

                builder.append(trace[stackIndex].getLineNumber())
                        .append(")");
                level = " -> ";
            }

            logChunk(logType, tag, builder.toString());
        }

        private void logBottomBorder(int logType, String tag) {
            //logChunk(logType, tag, BOTTOM_BORDER);
        }

        private void logDivider(int logType, String tag) {
            logChunk(logType, tag, MIDDLE_BORDER);
        }

        private void logContent(int logType, String tag, String chunk) {
            String[] lines = chunk.split(System.getProperty("line.separator"));
            for (String line : lines) {
                logChunk(logType, tag, line);
            }
        }

        protected void logChunk(int logType, String tag, String chunk) {
            String finalTag = formatTag(tag);
            switch (logType) {
                case android.util.Log.ERROR:
                    android.util.Log.e(finalTag, chunk);
                    break;
                case android.util.Log.INFO:
                    android.util.Log.i(finalTag, chunk);
                    break;
                case android.util.Log.VERBOSE:
                    android.util.Log.v(finalTag, chunk);
                    break;
                case android.util.Log.WARN:
                    android.util.Log.w(finalTag, chunk);
                    break;
                case android.util.Log.ASSERT:
                    android.util.Log.wtf(finalTag, chunk);
                    break;
                case android.util.Log.DEBUG:
                default:
                    android.util.Log.d(finalTag, chunk);
                    break;
            }
        }

        private String getSimpleClassName(String name) {
            int lastIndex = name.lastIndexOf(".");
            return name.substring(lastIndex + 1);
        }

        private String formatTag(String tag) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.equals(TAG, tag)) {
                return TAG + "-" + tag;
            }
            return TAG;
        }

        private String getTag() {
            String tag = LOCAL_TAG.get();
            if (tag != null) {
                LOCAL_TAG.remove();
                return tag;
            }
            return TAG;
        }

        private String createMessage(String message, Object... args) {
            return args.length == 0 ? message : String.format(message, args);
        }

        private int getMethodCount() {
            Integer count = LOCAL_METHOD_COUNT.get();
            int result = settings.getMethodCount();
            if (count != null) {
                LOCAL_METHOD_COUNT.remove();
                result = count;
            }
            if (result < 0) {
                throw new IllegalStateException("methodCount cannot be negative");
            }
            return result;
        }

        private int getStackOffset(StackTraceElement[] trace) {
            for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
                StackTraceElement e = trace[i];
                String name = e.getClassName();
                if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Log.class.getName())) {
                    return --i;
                }
            }
            return -1;
        }
    }

    private static class FileLoggerPrinter extends LoggerPrinter {
        private static final int LOG_FILE_MAX_SIZE = 10 * 1024 * 1024; // 10M

        public FileLoggerPrinter(Settings settings) {
            super(settings);
        }

        @Override
        protected boolean needLog() {
            return true;
        }

        @Override
        protected void logChunk(int logType, String tag, String chunk) {
            File logFile = settings.getLogFile();
            if (logFile == null) {
                return;
            }
            if (logFile.exists() && logFile.length() > LOG_FILE_MAX_SIZE) {
                logFile.delete();
            }
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            String logLevel;
            switch (logType) {
                case android.util.Log.ERROR:
                    logLevel = "E";
                    break;
                case android.util.Log.INFO:
                    logLevel = "I";
                    break;
                case android.util.Log.VERBOSE:
                    logLevel = "V";
                    break;
                case android.util.Log.WARN:
                    logLevel = "W";
                    break;
                case android.util.Log.ASSERT:
                    logLevel = "A";
                    break;
                case android.util.Log.DEBUG:
                default:
                    logLevel = "D";
                    break;
            }

            try {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(time())
                        .append(" ")
                        .append(logLevel)
                        .append("  (")
                        .append(String.valueOf(android.os.Process.myPid()))
                        .append(") ")
                        .append(chunk);
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private static String time() {
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return sdf.format(now);
        }
    }

    public static class Settings {
        private int methodCount = 2;
        private boolean showThreadInfo = true;
        private int methodOffset = 0;

        private LogLevel logLevel = Config.isDebug() ? LogLevel.FULL : LogLevel.NONE;
        private File logFile;
        private boolean logToFile;

        public Settings hideThreadInfo() {
            showThreadInfo = false;
            return this;
        }

        public Settings setMethodCount(int methodCount) {
            this.methodCount = methodCount;
            return this;
        }

        public Settings setLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Settings setMethodOffset(int offset) {
            this.methodOffset = offset;
            return this;
        }

        public Settings setLogFile(File file) {
            this.logFile = file;
            return this;
        }

        public Settings setLogToFile(boolean toFile) {
            this.logToFile = toFile;
            return this;
        }

        public int getMethodCount() {
            return methodCount;
        }

        public boolean isShowThreadInfo() {
            return showThreadInfo;
        }

        public LogLevel getLogLevel() {
            return logLevel;
        }

        public int getMethodOffset() {
            return methodOffset;
        }

        public File getLogFile() {
            return logFile;
        }

        public boolean isLogToFile() {
            return logToFile;
        }
    }

    public enum LogLevel {
        FULL, NONE
    }
}
