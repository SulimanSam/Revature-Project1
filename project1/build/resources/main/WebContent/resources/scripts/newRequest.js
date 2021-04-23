var x, i, j, l, ll, selElmnt, a, b, c;
/* Look for any elements with the class "custom-select": */
x = document.getElementsByClassName("custom-select");
l = x.length;
for (i = 0; i < l; i++) {
  selElmnt = x[i].getElementsByTagName("select")[0];
  ll = selElmnt.length;
  /* For each element, create a new DIV that will act as the selected item: */
  a = document.createElement("DIV");
  a.setAttribute("class", "select-selected");
  a.innerHTML = selElmnt.options[selElmnt.selectedIndex].innerHTML;
  x[i].appendChild(a);
  /* For each element, create a new DIV that will contain the option list: */
  b = document.createElement("DIV");
  b.setAttribute("class", "select-items select-hide");
  for (j = 1; j < ll; j++) {
    /* For each option in the original select element,
    create a new DIV that will act as an option item: */
    c = document.createElement("DIV");
    c.innerHTML = selElmnt.options[j].innerHTML;
    c.addEventListener("click", function(e) {
        /* When an item is clicked, update the original select box,
        and the selected item: */
        var y, i, k, s, h, sl, yl;
        s = this.parentNode.parentNode.getElementsByTagName("select")[0];
        sl = s.length;
        h = this.parentNode.previousSibling;
        for (i = 0; i < sl; i++) {
          if (s.options[i].innerHTML == this.innerHTML) {
            s.selectedIndex = i;
            h.innerHTML = this.innerHTML;
            y = this.parentNode.getElementsByClassName("same-as-selected");
            yl = y.length;
            for (k = 0; k < yl; k++) {
              y[k].removeAttribute("class");
            }
            this.setAttribute("class", "same-as-selected");
            break;
          }
        }
        h.click();
    });
    b.appendChild(c);
  }
  x[i].appendChild(b);
  a.addEventListener("click", function(e) {
    /* When the select box is clicked, close any other select boxes,
    and open/close the current select box: */
    e.stopPropagation();
    closeAllSelect(this);
    this.nextSibling.classList.toggle("select-hide");
    this.classList.toggle("select-arrow-active");
  });
}

function closeAllSelect(elmnt) {
  /* A function that will close all select boxes in the document,
  except the current select box: */
  var x, y, i, xl, yl, arrNo = [];
  x = document.getElementsByClassName("select-items");
  y = document.getElementsByClassName("select-selected");
  xl = x.length;
  yl = y.length;
  for (i = 0; i < yl; i++) {
    if (elmnt == y[i]) {
      arrNo.push(i)
    } else {
      y[i].classList.remove("select-arrow-active");
    }
  }
  for (i = 0; i < xl; i++) {
    if (arrNo.indexOf(i)) {
      x[i].classList.add("select-hide");
    }
  }
}

/* If the user clicks anywhere outside the select box,
then close all select boxes: */
document.addEventListener("click", closeAllSelect);



removeImg =document.getElementById("removeImg");

///////////////////////////////////////////////////////////////////////////
let userInfo ;
window.onload = function() {

  //Get User Information
  fetch(`http://localhost:9001/getUserInfo`)
  .then(
      function(response){
          const re = response.json();
          console.log(re);
          return re;
      }
  ).then(
      function(response2){
          
          console.log(response2);
          
          let user = document.getElementById("currentUser");
          user.innerText=response2.firstName+" "+response2.lastName;
          userInfo= response2;
      }
  ).catch(
      (e) => {
          console.log("exploded")
          
          console.log(e)
  }
  );
    removeImg.style.visibility = "hidden";
    //Check File API support
    if (window.File && window.FileList && window.FileReader) {
      var filesInput = document.getElementById("files");
      filesInput.addEventListener("change", function(event) {
        var files = event.target.files; //FileList object
        var output = document.getElementById("result");
        for (var i = 0; i < files.length; i++) {
          var file = files[i];
          //Only pics
          if (!file.type.match('image')){
            continue;
          }
            removeImg.style.visibility = "visible";
            var picReader = new FileReader();
            picReader.addEventListener("load", function(event) {
            
            var picFile = event.target;
            var span = document.createElement("span");
            span.setAttribute("class","imgHolder")
            span.innerHTML = ["<img class='thumbnail' style='width:200px; border-width:3px; border-style:solid; border-color:#000; margin:4px;' src='" + picFile.result + "'" +
              "title='" + escape(picFile.name) + "'/>"].join('');
            output.insertBefore(span, null);
            
          });
          //Read the image
          picReader.readAsDataURL(file);
          
        }
      });
    } else {
      alert("Your browser does not support File API");
    }
  }

///////////////////////////////////////////////


  removeImg.addEventListener("click",function(event) {
    var elements = document.getElementsByClassName("imgHolder");
    while(elements.length > 0){
        elements[0].parentNode.removeChild(elements[0]);
    }
      
    removeImg.style.visibility = "hidden";

  });


////////////////////////////////////////////////////////////////////////
  document.getElementById("newRequestForm").addEventListener("submit",function(event) {
    event.preventDefault();
    //////////////////////////////////////////////////////////////////////////
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
    if(xhttp.readyState===4 && xhttp.status===200){            
        let re = xhttp.responseText;
        console.log(re);
        if ((re==1) ){

          window.swal({ 
            title: 'Done....',
            type: 'success',
            timer: 4000,
            showConfirmButton: true
          });
           
        }else{
            window.swal({ 
                title: "something went wrong",
                type: 'warning',
                showConfirmButton: true
              })
        }
    }
  }    
    xhttp.open("POST", `http://localhost:9001/newRequest`);

    let imgHolder = document.getElementsByClassName("imgHolder");
    let selection = document.getElementById("type");
    let selected = selection.value;
    let amount = document.getElementById("amount").value;
    let description = document.getElementById("description").value;
    let imags=[];

    
    if(imgHolder.length == 0){
        swal("Select At lest one Image", "and try again", "warning");
        return;
    }
    if(selected == 0){
        swal("Select the Reimbursement Type", "and try again", "warning");
        return;
    }
    let i=0;
    for (const element of imgHolder) {
      imags[i]=element.firstElementChild.src;
      
      i++;
      
    }

    let requestinfo = {
      "userName":userInfo.userName,
      "images":imags,
      "amount":amount,
      "description":description,
      "reimbursementType":selected
    }
    
    xhttp.setRequestHeader("content-type", "application/json");
    console.log(requestinfo);
    xhttp.send(JSON.stringify(requestinfo));

    window.swal({
        title: 'Creating',
        allowEscapeKey: false,
        allowOutsideClick: false,
        showConfirmButton: false,
        timer: 4000,
        onOpen: () => {
          swal.showLoading();
        }
        
      })
      
    

  });




