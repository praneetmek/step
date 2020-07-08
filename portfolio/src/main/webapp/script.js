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

/**
 * Adds a random greeting to the page.
 */

max_height = "5000px"

var projdiv = document.getElementsByClassName("one-project");
var i;
for (i = 0; i < projdiv.length; i++) {

  projdiv[i].addEventListener("mouseover", function() {
    if (this.style.maxHeight !== max_height) {
      console.log("mouseover")
      this.style.backgroundPosition = "8% 100%"
    }
  })

  projdiv[i].addEventListener("mouseout", function() {
    if (this.style.maxHeight !== max_height) {
      console.log("mouseexit")
      this.style.backgroundPosition = "0% 100%"
    }
  })

  projdiv[i].addEventListener("click", function() {
    var head = this.getElementsByTagName("h4")[0];
    if (this.style.maxHeight !== max_height) {
      this.style.maxHeight = max_height;
      this.style.backgroundPosition = "100% 100%";
      this.style.color = "white"

      this.getElementsByTagName('a')[0].style.color = "white"
      setTimeout(function() {
        this.scrollIntoView(false)
      }, 100)
    } else {


      this.style.maxHeight = "4vw"
      this.style.backgroundPosition = "8% 100%";
      this.style.color = "black"
      this.getElementsByTagName('a')[0].style.color = "black"


    }
  })

}

function readFromServlet() {
    var selectable=document.getElementById("num-comms");
    var number=selectable.options[selectable.selectedIndex].value;
    fetch('/data?numcomm='+number).then(response => response.json()).then((json_list)=>{
        console.log(json_list);
        document.getElementById("servlet-test").innerText=json_list;
    })
}

document.getElementById("num-comms").addEventListener("change", function(){
    console.log('changed')
    readFromServlet()
});

function deleteAllComments(){
    console.log('here')
    fetch('/delete-data',{
        method: 'POST'
    }).then(response => response.text()).then(text=>{
        readFromServlet();        
    })
}

function createMap() {
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 37.422, lng: -122.084}, zoom: 16});
}
