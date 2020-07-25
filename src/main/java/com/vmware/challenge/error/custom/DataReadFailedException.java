package com.vmware.challenge.error.custom;

public class DataReadFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4969183684621600257L;

	public DataReadFailedException(String id,String message) {
		super("Data read failed from output file :"+id+" \n"+message);
	}
}
