let userinfo;
window.onload = function() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const reimbID11 = urlParams.get("reimbID");
    console.log(reimbID11);

    fetch('http://localhost:9001/'+reimbID11+'/getImages')
    .then(
        function(response){
            const re = response.json();
            return re;
        }
    ).then(
        function(response2){
            console.log("reimbID11");
            ourDOMManipulation(response2);
        }
    ).catch(
        (e) => {
            console.log("Exception");
            console.log(e);
    }
    );
    ///////////////////////////////////////////////////////////////////////
    fetch('http://localhost:9001/getUserInfo')
    .then(
        function(response){
            const re = response.json();
            
            return re;
        }
    ).then(
        function(response2){
            
            let user = document.getElementById("currentUser");
            user.innerText=response2.firstName+" "+response2.lastName;

            userinfo=response2;
            
        }
    ).catch(
        (e) => {
            console.log("Exception");
            console.log(e);
    }
    );
  }


  //////////////////////////////////////////////////////////////////////////////////////



  function ourDOMManipulation(json){
    let cont= document.getElementById("container");
    var output = document.getElementById("result");
    let arr=[];
    let img;
    let span;
    let i = 1;
    for(let val of json){
        img = document.createElement("img");
        span= document.createElement("span");
        Object.assign(img, {
            id:i,
            className: 'thumbnail',
            src: val,
            style: 'width:200px; border-width:3px; border-style:solid; border-color:#000; margin:4px;', 
            
            onclick: function () {
                modal.style.display = "block";
                modalImg.src = this.src;
                captionText.innerHTML = this.alt;
            }
          })
        span.setAttribute("class","imgHolder myImg");
        span.appendChild(img);
        output.insertBefore(span,null);
        i++;
        
        
    }
    
    cont.appendChild(output);
    //swal("Hello world!");


    ////////////////////////////////////////////////////////
// Get the modal
var modal = document.getElementById("myModal");

// Get the image and insert it inside the modal - use its "alt" text as a caption
var modalImg = document.getElementById("img01");
var captionText = document.getElementById("caption");


// Get the <span> element that closes the modal
var span1 = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span1.onclick = function() {
  modal.style.display = "none";
}

}









 