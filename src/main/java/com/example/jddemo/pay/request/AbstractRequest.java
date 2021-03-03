package com.example.jddemo.pay.request;

import java.io.Serializable;


public abstract class AbstractRequest implements Serializable{

    private static final long serialVersionUID = 1717442845820713651L;
    public abstract void requestCheck();

}
