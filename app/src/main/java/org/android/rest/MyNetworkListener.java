package org.android.rest;


public interface MyNetworkListener<T> {
   public void getResult(T result);
   public void getException(NetworkExceptionHandler error);
}
