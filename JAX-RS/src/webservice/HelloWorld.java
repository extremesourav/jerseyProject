package webservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/getHello")
public class HelloWorld  {

	@GET
	@Path("/showString")
	public Response printMessage(){
		String output = "Hello WS";
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

	String output = "Jersey say : " + msg;

	return Response.status(200).entity(output).build();

	}
	@GET
	@Path("/getQParam")
	public Response getQueryMsg(@QueryParam("str") String s){
		String output = "Hello " + s;
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/postFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response getFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			FormDataMultiPart multiPart)
	{
	try{
		String uploadedFileLocation = "C:/A/"+fileDetail.getFileName();
		
		writeToFile(uploadedInputStream, uploadedFileLocation);
		 
        String output = "File Uploaded ";
		
	}
	catch(Exception e){
		e.printStackTrace();
	}
	String output = "File Uploaded";
	return Response.status(200).entity(output ).build();
	}
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {
	 
			try {
				OutputStream out = new FileOutputStream(new File(
						uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];
	 
				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {
	 
				e.printStackTrace();
			}
	}
	@GET
	@Path("/download")
	@Produces("text/plain")
	public Response getTextFile() {

		File file = new File("c:/A/kano.mp3");

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=\"kano.mp3\"");
		return response.build();

	}
	
	@GET
	@Path("/getJson")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getTrackInJSON() {
 
		Person person = new Person();
		
		person.setAddress("Kolkata");
		person.setName("Sourav");
		person.setAge(24);
 
		return person;
 
	}
	
	@POST
	@Path("/postJson")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Person person) {
 
		String result = "Person saved : " + person;
		System.out.println(result);
		return Response.status(201).entity(result).build();
 
	}

}
