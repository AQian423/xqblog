package com.qinweizhao.blog.exception;

/**
 * Image format exception.
 *
 * @author ZhiXiang Yuan
 * @since 2020/08/10 02:11
 */
public class ImageFormatException extends BadRequestException {

    public ImageFormatException(String message) {
        super(message);
    }

    public ImageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
