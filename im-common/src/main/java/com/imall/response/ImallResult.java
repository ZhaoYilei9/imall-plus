package com.imall.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImallResult<T> {
	private int code;
	private T data;
	private String msg;

	public static <T> ImallResult<T> success() {
		ImallResult<T> imallResult = new ImallResult<>();
		return imallResult;
	}

	public static <T> ImallResult<T> success(T data) {
		ImallResult<T> imallResult = new ImallResult<>(data);
		return imallResult;
	}

	private ImallResult(T data) {
		this.code = 200;
		this.data = data;
	}

	private ImallResult() {
		this.code = 200;
		this.data = null;
	}

	private ImallResult(String msg) {
		this.code = 500;
		this.msg = msg;
	}

	public static ImallResult<Void> errorMsg(String msg) {
		ImallResult<Void> imallResult = new ImallResult<>(msg);
		return imallResult;
	}

	public static <T> ImallResult<T> errorMap(T data) {
		return new ImallResult<T>(501, "error", data);
	}

	public static <T> ImallResult<T> errorokenMsg(String msg) {
		return new ImallResult<T>(502, msg, null);
	}

	public static <T> ImallResult<T> errorException(String msg) {
		return new ImallResult<T>(555, msg, null);
	}

	public ImallResult(int code, String msg, T data) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

}
