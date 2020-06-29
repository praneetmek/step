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
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

var projdiv = document.getElementsByClassName("one-project");
var i;
for (i=0 ; i < projdiv.length; i++){

  projdiv[i].addEventListener("mouseover", function(){
    if(this.style.maxHeight!=="500px"){
      console.log("mouseover")
      this.style.backgroundPosition="8% 100%"
    }
  })

  projdiv[i].addEventListener("mouseout", function(){
    if(this.style.maxHeight!=="500px"){
      console.log("mouseexit")
      this.style.backgroundPosition="0% 100%"
    }
  })

  projdiv[i].addEventListener("click", function(){
    var head = this.getElementsByTagName("h4")[0];
    if (this.style.maxHeight!=="500px"){
      this.style.maxHeight="500px"
      this .style.backgroundPosition="100% 100%";
      this.style.color="white"
    }
    else{
      this.style.maxHeight="4vw"
      this.style.backgroundPosition="8% 100%";
      this.style.color="black"

    }
  })

  

}