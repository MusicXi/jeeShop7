package com.zlkj.ssm.shop.front.service;import com.zlkj.ssm.shop.core.Services;import com.zlkj.ssm.shop.front.entity.Address;public interface AddressService extends Services<Address> {	/**	 * 设置指定的地址为默认地址	 * @param address	 */	void setAddressDefault(Address address);}