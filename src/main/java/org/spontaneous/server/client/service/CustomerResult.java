package org.spontaneous.server.client.service;

import java.util.ArrayList;
import java.util.List;

import org.spontaneous.server.usermanagement.to.CustomerTO;

public class CustomerResult {
	
	private List<CustomerTO> customerlist;

	public List<CustomerTO> getCustomerlist() {
		if (customerlist == null) {
			customerlist = new ArrayList<CustomerTO>();
		}
		return customerlist;
	}

	public void setCustomerlist(List<CustomerTO> customerlist) {
		this.customerlist = customerlist;
	}
	
	public boolean addCustomer(CustomerTO to) {
		if (customerlist == null) {
			customerlist = new ArrayList<CustomerTO>();
		}
		return customerlist.add(to);
	}
}
