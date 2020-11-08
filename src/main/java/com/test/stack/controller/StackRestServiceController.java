package com.test.stack.controller;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.test.stack.service.StackRestService;
import com.test.stack.service.StackRestServiceImpl;

@Path("/stack")
public class StackRestServiceController {

	@POST
	@Path("/push/{item}")
	public Response push(@PathParam("item") String item) {
		String result;
		ResponseBuilder responseBuilder;
		StackRestService stackRestServiceImpl = new StackRestServiceImpl();
		if (stackRestServiceImpl.push(item)) {
			result = item + " has been pushed to stack successfully!!!";
			responseBuilder = Response.status(200).entity(result);
		} else {
			result = "Not able to push the " + item + " to stack.";
			responseBuilder = Response.status(400).entity(result);
		}
		return responseBuilder.build();
	}

	@DELETE
	@Path("/pop")
	public Response pop() {
		String result;
		ResponseBuilder responseBuilder;
		StackRestService stackRestServiceImpl = new StackRestServiceImpl();
		if (stackRestServiceImpl.pop()) {
			result = "The item has been popped from stack successfully!!!";
			responseBuilder = Response.status(200).entity(result);
		} else {
			result = "Not able to pop the item from stack.";
			responseBuilder = Response.status(400).entity(result);
		}
		return responseBuilder.build();
	}

}
