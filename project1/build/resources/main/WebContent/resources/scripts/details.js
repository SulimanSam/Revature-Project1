let userinfo;
let reimbursement11;



window.onload = function() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const reimbID11 = urlParams.get("reimbID");
    reimbursement11=reimbID11;
    

    fetch('http://localhost:9001/'+reimbID11+'/getImages')
    .then(
        function(response){
            const re = response.json();
            return re;
        }
    ).then(
        function(response2){
            console.log("test");
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

    /////////////////////////////////////////////////////////////////
    fetch('http://localhost:9001/'+reimbID11+'/getReimbursement')
    .then(
        function(response){
            const re = response.json();
            return re;
        }
    ).then(
        function(response2){
            console.log(response2);
            let reimbId = document.getElementById("reimb-Id");
            let reimbAmount = document.getElementById("reimb-amount");
            let submitted = document.getElementById("submitted");
            let resolved = document.getElementById("resolved");
            let author = document.getElementById("author");
            let resolver = document.getElementById("resolver");
            let status = document.getElementById("status");
            let type = document.getElementById("type");
            let description = document.getElementById("description");
            
            if((response2.status=="approved")||(response2.status=="denied")){
                document.getElementById("btn1").style.display ="none";
                document.getElementById("btn2").style.display ="none";

            }
            reimbId.value=response2.reimbursementID;
            reimbAmount.value=response2.amount;
            submitted.value=new Date(response2.submitDate).toISOString().slice(0, 10);
            resolved.value=(response2.resolveDate== null)? "N/A":new Date(response2.resolveDate).toISOString().slice(0, 10);
            author.value=response2.author;
            resolver.value=(response2.resolver== null)? "N/A":response2.resolver;
            status.value=response2.status;
            type.value=response2.type;
            description.value=response2.description;
            
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
    let cont= document.getElementById("container2");
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




/////////////////////////////////////////////
document.getElementById("btn1").addEventListener("click",function(event) {
                
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){

        if(xhttp.readyState===4 && xhttp.status===200){
             
            window.swal({ 
                title: 'Done....',
                type: 'success',
                timer: 4000,
                showConfirmButton: true
              });
              window.location.href = "http://localhost:9001/ManagerHome.html";
        }
    }
       
    xhttp.open("POST", `http://localhost:9001/reimbursementApprove2`);
    
    let requestinfo = {
      "userName":reimbursement11,
      "images":null,
      "amount":null,
      "description":userinfo.userID,
      "reimbursementType":null
    };
    
    xhttp.setRequestHeader("content-type", "application/json");
    
    xhttp.send(JSON.stringify(requestinfo));

    window.swal({
        title: 'Approving',
        allowEscapeKey: false,
        allowOutsideClick: false,
        showConfirmButton: false,
        timer: 4000,
        onOpen: () => {
          swal.showLoading();
        }
        
    });
      
});


    /////////////////////////////////////////////
document.getElementById("btn2").addEventListener("click",function(event) {
    
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){

        if(xhttp.readyState===4 && xhttp.status===200){
             
            window.swal({ 
                title: 'Done....',
                type: 'success',
                timer: 4000,
                showConfirmButton: true
              });
              window.location.href = "http://localhost:9001/ManagerHome.html";
        }
    }
       
    xhttp.open("POST", `http://localhost:9001/reimbursementDeny2`);

    let requestinfo = {
      "userName":reimbursement11,
      "images":null,
      "amount":null,
      "description":userinfo.userID,
      "reimbursementType":null
    };
    
    xhttp.setRequestHeader("content-type", "application/json");
    
    xhttp.send(JSON.stringify(requestinfo));

    window.swal({
        title: 'denying',
        allowEscapeKey: false,
        allowOutsideClick: false,
        showConfirmButton: false,
        timer: 4000,
        onOpen: () => {
          swal.showLoading();
        }
        
    });
      
});







 