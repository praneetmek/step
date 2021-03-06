// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);;

    PreparedQuery commentQuery = datastore.prepare(query);

    int numberOfComments = getNumberOfComments(request);

    ArrayList<Comment> comments = getComments(commentQuery, numberOfComments);
    
    String jsonString = convertToJsonUsingGson(comments);
    response.setContentType("text/json;");
    response.getWriter().println(jsonString);
  }


  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String newComment = request.getParameter("comment-box");
      long timestamp = System.currentTimeMillis();

      UserService userService = UserServiceFactory.getUserService();



      Entity commentEntity=new Entity("Comment");

      commentEntity.setProperty("user", userService.getCurrentUser().getEmail());
      commentEntity.setProperty("text", newComment);
      commentEntity.setProperty("timestamp", timestamp);
      datastore.put(commentEntity);

      response.sendRedirect("/index.html#comment-area");

  }

  private String convertToJsonUsingGson(ArrayList<Comment> comments) {
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }

  private int getNumberOfComments(HttpServletRequest request){
      String numCommString = request.getParameter("numcomm");
      int numComm;
      if (numCommString == ""){
          return 20;
      }
      try{
          numComm=Integer.parseInt(numCommString);
          return numComm;
      } catch (NumberFormatException e){
          return 20;
      }
  }


  private ArrayList<Comment> getComments(PreparedQuery commQuery, int numComms){
    ArrayList<Comment> comments = new ArrayList<>();
    int i=0;
    for (Entity entity : commQuery.asIterable()){
        if(i==numComms){
            break;
        }
        String comment= (String) entity.getProperty("text");
        String accountName = (String) entity.getProperty("user");

        comments.add(new Comment(accountName,comment));
        i++;
    }
    return comments;
  }


  public final class Comment{
        private final String accountName;
        private final String text;
        public Comment(String accountName, String text){
            this.accountName=accountName;
            this.text=text;
        }
        public String getAccountName(){
            return accountName;
        }
        public String getText(){
            return text;
        }
  }
}
