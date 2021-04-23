
let userinfo;
window.onload = function() {
    

    fetch(`http://localhost:9001/getUserInfo`)
    .then(
        function(response){
            const re = response.json();
            
            return re;
        }
    ).then(
        function(response2){
    
            
            
            let user = document.getElementById("currentUser");
            user.innerText=response2.firstName+" "+response2.lastName;
            let email=document.getElementById("email");
            let username=document.getElementById("username");
            let first=document.getElementById("firstname");
            let last=document.getElementById("lastname");
            email.value=response2.email;
            username.value=response2.userName;
            first.value=response2.firstName;
            last.value=response2.lastName;
            last.focus();
            userinfo=response2;
        }
    ).catch(
        (e) => {
            console.log("Exception");
            console.log(e);
    }
    );
  }





//<div class="row" id="password-container">
  //                              <div class="col-md-12">
    //                              <div class="form-group">
      //                              <label class="bmd-label-floating">Adress</label>
        //                            <input type="text" class="form-control">
          //                        </div>
            //                    </div>
              //              </div>
const updateType = document.getElementById("update-type");
const updateForm = document.getElementById("update-form");
const passwordContainar = document.getElementById("password-container");

let div1 = document.createElement("div");
let div2 = document.createElement("div");
let div3 = document.createElement("div");
div1.setAttribute("class","col-md-4");
div2.setAttribute("class","col-md-4");
div3.setAttribute("class","col-md-4");

let lable1 =  document.createElement("lable");
let lable2 =  document.createElement("lable");
let lable3 =  document.createElement("lable");
lable1.setAttribute("class","bmd-label-floating");
lable2.setAttribute("class","bmd-label-floating");
lable3.setAttribute("class","bmd-label-floating");
lable1.innerText = "New Password";
lable2.innerText = "Re-New Password";
lable3.innerText = "Old Password";


let newPass = document.createElement("input");
let reNewPass = document.createElement("input");
let oldPass = document.createElement("input");
newPass.setAttribute("class","form-control");
reNewPass.setAttribute("class","form-control");
oldPass.setAttribute("class","form-control");
newPass.setAttribute("id","newPass");
reNewPass.setAttribute("id","reNewPass");
oldPass.setAttribute("id","oldPass");
newPass.required = true;
reNewPass.required = true;
oldPass.required = true;


let div11 = document.createElement("div");
let div21 = document.createElement("div");
let div31 = document.createElement("div");
div11.setAttribute("class","form-group");
div21.setAttribute("class","form-group");
div31.setAttribute("class","form-group");

div11.appendChild(lable1);
div11.appendChild(newPass);
div21.appendChild(lable2);
div21.appendChild(reNewPass);
div31.appendChild(lable3);
div31.appendChild(oldPass);

div1.appendChild(div11);
div2.appendChild(div21);
div3.appendChild(div31);



lable1.innerText = "New Password";
lable2.innerText = "Re-New Password";
lable3.innerText = "Old Password";





updateType.addEventListener('change', function() {
    if (this.checked) {
        passwordContainar.appendChild(div1);
        passwordContainar.appendChild(div2);
        passwordContainar.appendChild(div3);
    } else {
        passwordContainar.removeChild(div1);
        passwordContainar.removeChild(div2);
        passwordContainar.removeChild(div3);

    }
  });


  document.getElementById("update-form").addEventListener("submit",function(event) {
    event.preventDefault();

    ////////////////////////////////////////new request//////////////////////////////////////////////
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
    if(xhttp.readyState===4 && xhttp.status===200){            
        let re = xhttp.responseText;
        console.log(re);
        if (re==1){

          window.swal({ 
            title: 'Profile Updated',
            type: 'success',
            timer: 4000,
            showConfirmButton: true
          }) 
        }else{
            window.swal({ 
                title: "Your Old Password is wrong",
                type: 'warning',
                showConfirmButton: true
              })
        }
    }
  }    
    xhttp.open("POST", `http://localhost:9001/userUpdate`);
    let firstName =document.getElementById("firstname").value;
    let lastName =document.getElementById("lastname").value;
    let newPassword ;
    let reNewPassword;
    let oldPassword ;
    let userInfo;

    if(updateType.checked){
        newPassword =document.getElementById("newPass").value;
        reNewPassword =document.getElementById("reNewPass").value;
        oldPassword =document.getElementById("oldPass").value;
        userInfo = {
            "userID":userinfo.userID,
            "userName":userinfo.userName,
            "firstName":firstName,
            "lastName":lastName,
            "password":newPassword,
            "email":userinfo.email,
            "date":null,
            "isChange":1,
            "role":oldPassword
          }
    }else{
        userInfo = {
            "userID":userinfo.userID,
            "userName":userinfo.userName,
            "firstName":firstName,
            "lastName":lastName,
            "password":null,
            "email":userinfo.email,
            "date":null,
            "isChange":0,
            "role":null
          }
    }
    if(newPassword===reNewPassword){
        

    }else{
        window.swal({ 
            title: "Password does not match",
            type: 'warning',
            showConfirmButton: true
          })
        return;
    }


    
    xhttp.setRequestHeader("content-type", "application/json");
    console.log(userInfo);
    console.log(JSON.stringify(userInfo));
    xhttp.send(JSON.stringify(userInfo));

    window.swal({
        title: 'Updating',
        allowEscapeKey: false,
        allowOutsideClick: false,
        showConfirmButton: false,
        timer: 8000,
        onOpen: () => {
          swal.showLoading();
        }
        
      })
    
      
    

  });