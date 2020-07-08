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
import com.google.appengine.api.datastore.*;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/delete-data")
public class DataDelete extends HttpServlet {
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
  }


  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      System.out.println("hello this is a print");
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Query query = new Query("Comment");
      PreparedQuery commentQuery = datastore.prepare(query);
      response.setContentType("text");

      for(Entity entity: commentQuery.asIterable()){
          Key k = entity.getKey();
          datastore.delete(k);
      }

  }

}