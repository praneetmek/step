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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;


@WebServlet("/loginstatus")
public class LoginStatus extends HttpServlet {

    public final class JsonHelper{
        private final boolean loggedIn;
        private final String logInOutURL;
        public JsonHelper(Boolean loggedIn, String logInOutURL){
            this.loggedIn=loggedIn;
            this.logInOutURL=logInOutURL;
        }
        public boolean getLoggedIn(){
            return loggedIn;
        }
        public String getLogInOutURL(){
            return logInOutURL;
        }
    }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    
    UserService userService = UserServiceFactory.getUserService();
    Gson gson = new Gson();
    response.setContentType("application/json;");

    JsonHelper jsonHelper;

    if(userService.isUserLoggedIn()){
        jsonHelper = new JsonHelper(true,userService.createLogoutURL("/index.html"));

    }
    else{
        jsonHelper = new JsonHelper(false,userService.createLoginURL("/index.html"));
    }
    String jsonString = gson.toJson(jsonHelper);
    response.getWriter().println(jsonString);


  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

  }

}
