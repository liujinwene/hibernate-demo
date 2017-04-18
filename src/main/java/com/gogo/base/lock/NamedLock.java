package com.gogo.base.lock;

import java.util.concurrent.Callable;

public abstract interface NamedLock {
	abstract <T> T enter(Callable<T> callback) throws Exception;
}
