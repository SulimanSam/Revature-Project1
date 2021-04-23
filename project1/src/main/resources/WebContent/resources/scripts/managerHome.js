let Reimb ;
let cont= document.getElementById("container");
let currentUser;
window.onload = function() {

    //Get User Information
  fetch(`http://localhost:9001/getUserInfo`)
  .then(
      function(response){
          const re = response.json();
          
          return re;
      }
  ).then(
      function(response2){
          
          let user = document.getElementById("currentUser");
          currentUser = response2;
          user.innerText=response2.firstName+" "+response2.lastName;
          if(response2.isChange==1){
            window.swal({
                title: 'Please Update Your Password',
                type: 'warning',
                allowEscapeKey: false,
                allowOutsideClick: false,
                showConfirmButton: true,
                timer: 8000,
                
              })
          }
      }
  ).catch(
      (e) => {
          console.log("exploded")
          
          console.log(e)
  }
  );

      //Get Users Information
      fetch(`http://localhost:9001/getAllUsers`)
      .then(
          function(response){
              const re = response.json();
              
              return re;
          }
      ).then(
          function(response2){
              
              let users = document.getElementById("users");
              for (const user of response2) {
                  let option = document.createElement("option");
                  option.setAttribute("value",user.userName)
                  option.innerText = user.firstName+" "+user.lastName;
                  users.appendChild(option);
              }      
          }
      ).catch(
          (e) => {
              console.log("exploded")
              
              console.log(e)
      }
      );

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){

       if(xhttp.readyState===4 && xhttp.status===200){

        let info = xhttp.responseText;
        
        let jsonResponse = JSON.parse(info);
            
            Reimb = jsonResponse.json;
            addReimbursementinfo(jsonResponse.info);
            FilteredTable(jsonResponse.json);
       }
   }

    xhttp.open("GET", `http://localhost:9001/getAllReimbursements`);
    
    xhttp.send();
}


function addReimbursementinfo(info){
    document.getElementById("ApprovedCard").innerText= info[0];
    document.getElementById("DeniedCard").innerText= info[1];
    document.getElementById("ApprovedAmount").innerText= "$ "+info[2];
    document.getElementById("Pending").innerText= info[3];
}
////////////////////
function userTable(json,stat,author){

    cont.innerHTML="";
    let table= document.createElement("table");
    let thead= document.createElement("thead");
    let tbody= document.createElement("tbody");
    table.setAttribute("class", "table table-striped");
    thead.setAttribute("class","text-primary");
    
    let th= document.createElement("tr");
    for(var i =1;i<12;i++){
       
        let td =document.createElement("td");
        switch(i){
        case 1: td.innerText= "ID";
        
        break;
        case 2: td.innerText= "Amount";
        
        break;
        case 3: td.innerText= "Submittion Date";
        
        break;
        case 4: td.innerText= "Resolve Date";
        
        break;
        case 5: td.innerText= "Description";
        
        break;
        case 10: td.innerText= "";
        
        break;
        case 11: td.innerText= "";
        
        break;
        case 6: td.innerText= "Author";
        
        break;
        case 7: td.innerText= "Resolver";
        
        break;
        case 8: td.innerText= "Status";
        
        break;
        case 9: td.innerText= "Type";
        
        break;
        }
        th.appendChild(td);
    }
    thead.appendChild(th);
    
    
    for(let val of json){
        if(author)
        if( author!=val.author){
            
            continue;
        }
        
        let tr= document.createElement("tr");
        
        
        let approveFrom= document.createElement("form");
        approveFrom.setAttribute("action","http://localhost:9001/reimbursementApprove");
        approveFrom.setAttribute("target","_self");
        approveFrom.setAttribute("method","POST");
        let deniedFrom= document.createElement("form");
        deniedFrom.setAttribute("action","http://localhost:9001/reimbursementDeny");
        deniedFrom.setAttribute("target","_self");
        deniedFrom.setAttribute("method","POST");

        let inputReimb = document.createElement("input");
        let inputResolver = document.createElement("input");
        inputReimb.setAttribute("name","reimbID");
        inputResolver.setAttribute("name","resolverID");
        inputReimb.setAttribute("type","hidden");
        inputResolver.setAttribute("type","hidden");
        inputReimb.value=val.reimbursementID;
        inputResolver.value=currentUser.userID;

        let inputReimb2 = document.createElement("input");
        let inputResolver2 = document.createElement("input");
        inputReimb2.setAttribute("name","reimbID");
        inputResolver2.setAttribute("name","resolverID");
        inputReimb2.setAttribute("type","hidden");
        inputResolver2.setAttribute("type","hidden");
        inputReimb2.value=val.reimbursementID;
        inputResolver2.value=currentUser.userID;

        let approveButton = document.createElement("button");
        let denyButton = document.createElement("button");
        approveButton.setAttribute("class","btn btn-success");
        denyButton.setAttribute("class","btn btn-danger");
        approveButton.innerText = "Approve";
        denyButton.innerText = "Denie";

        approveFrom.appendChild(inputReimb2);
        approveFrom.appendChild(inputResolver2);
        approveFrom.appendChild(approveButton);
        deniedFrom.appendChild(inputReimb);
        deniedFrom.appendChild(inputResolver);
        deniedFrom.appendChild(denyButton);

        
        for(var i =1;i<12;i++){
            
            let td =document.createElement("td");
            switch(i){
            case 1: td.innerHTML= '<a href="./details.html?reimbID='+val.reimbursementID+'">'+"REIM"+val.reimbursementID+'</a>';
            break;
            case 2: td.setAttribute("class","text-primar");
            td.innerText= "$ "+val.amount;
            
            break;
            case 3: td.innerText= new Date(val.submitDate).toISOString().slice(0, 10);
            
            break;
            case 4: td.innerText= (val.resolveDate== null)? "N/A":new Date(val.resolveDate).toISOString().slice(0, 10);
            
            break;
            case 5: td.innerText= val.description;
            
            break;
            case 10:
            if(val.status=="pending")
            td.appendChild(approveFrom);
            
            break;
            case 11:
            if(val.status=="pending")
            td.appendChild(deniedFrom);
            
            break;
            case 6: td.innerText= val.author;
            
            break;
            case 7: td.innerText= val.resolver;
            
            break;
            case 8: td.innerText= val.status;
            
            break;
            case 9: td.innerText= val.type;
            
            break;
            }
            tr.append(td);
        }
        
        tbody.append(tr);
    }
    
    
    table.appendChild(thead);
    table.appendChild(tbody);
    cont.appendChild(table);
    //swal("Hello world!");
    
}


////////////////////
function FilteredTable(json,stat,username){
    
    cont.innerHTML="";
    let table= document.createElement("table");
    let thead= document.createElement("thead");
    let tbody= document.createElement("tbody");
    table.setAttribute("class", "table table-striped");
    thead.setAttribute("class","text-primary");
    
    let th= document.createElement("tr");
    for(var i =1;i<12;i++){
       
        let td =document.createElement("td");
        switch(i){
        case 1: td.innerText= "ID";
        
        break;
        case 2: td.innerText= "Amount";
        
        break;
        case 3: td.innerText= "Submittion Date";
        
        break;
        case 4: td.innerText= "Resolve Date";
        
        break;
        case 5: td.innerText= "Description";
        
        break;
        case 10: td.innerText= "";
        
        break;
        case 11: td.innerText= "";

        break;
        case 6: td.innerText= "Author";
        
        break;
        case 7: td.innerText= "Resolver";
        
        break;
        case 8: td.innerText= "Status";
        
        break;
        case 9: td.innerText= "Type";
        
        break;
        }
        th.appendChild(td);
    }
    thead.appendChild(th);
    
    
    for(let val of json){
        if(val.status!=stat && stat ){
            continue;
        }
        let tr= document.createElement("tr");


        let approveFrom= document.createElement("form");
        approveFrom.setAttribute("action","http://localhost:9001/reimbursementApprove");
        approveFrom.setAttribute("target","_self");
        approveFrom.setAttribute("method","POST");
        let deniedFrom= document.createElement("form");
        deniedFrom.setAttribute("action","http://localhost:9001/reimbursementDeny");
        deniedFrom.setAttribute("target","_self");
        deniedFrom.setAttribute("method","POST");

        let inputReimb = document.createElement("input");
        let inputResolver = document.createElement("input");
        inputReimb.setAttribute("name","reimbID");
        inputResolver.setAttribute("name","resolverID");
        inputReimb.setAttribute("type","hidden");
        inputResolver.setAttribute("type","hidden");
        inputReimb.value=val.reimbursementID;
        inputResolver.value=currentUser.userID;

        let inputReimb2 = document.createElement("input");
        let inputResolver2 = document.createElement("input");
        inputReimb2.setAttribute("name","reimbID");
        inputResolver2.setAttribute("name","resolverID");
        inputReimb2.setAttribute("type","hidden");
        inputResolver2.setAttribute("type","hidden");
        inputReimb2.value=val.reimbursementID;
        inputResolver2.value=currentUser.userID;

        let approveButton = document.createElement("button");
        let denyButton = document.createElement("button");
        approveButton.setAttribute("class","btn btn-success");
        denyButton.setAttribute("class","btn btn-danger");
        approveButton.innerText = "Approve";
        denyButton.innerText = "Denie";

        approveFrom.appendChild(inputReimb2);
        approveFrom.appendChild(inputResolver2);
        approveFrom.appendChild(approveButton);
        deniedFrom.appendChild(inputReimb);
        deniedFrom.appendChild(inputResolver);
        deniedFrom.appendChild(denyButton);
        
        
        for(var i =1;i<12;i++){
            
            let td =document.createElement("td");
            switch(i){
            case 1: td.innerHTML= '<a href="./details.html?reimbID='+val.reimbursementID+'">'+"REIM"+val.reimbursementID+'</a>';
            
            break;
            case 2: td.setAttribute("class","text-primar");
            td.innerText= "$ "+val.amount;
            
            break;
            case 3: td.innerText= new Date(val.submitDate).toISOString().slice(0, 10);
            
            break;
            case 4: td.innerText= (val.resolveDate== null)? "N/A":new Date(val.resolveDate).toISOString().slice(0, 10);
            
            break;
            case 5: td.innerText= val.description;
            
            break;
            case 10:
            if(val.status=="pending"){
            td.appendChild(approveFrom);}
            
            break;
            case 11:
            if(val.status=="pending"){
            td.appendChild(deniedFrom);}
            
            break;
            case 6: td.innerText= val.author;
            
            break;
            case 7: td.innerText= val.resolver;
            
            break;
            case 8: td.innerText= val.status;
            
            break;
            case 9: td.innerText= val.type;
            
            break;
            }
            tr.append(td);
        }
        
        tbody.append(tr);
    }
    
    
    table.appendChild(thead);
    table.appendChild(tbody);
    cont.appendChild(table);
    //swal("Hello world!");
    
}


///////////////////////////////////////////////////////

let r1 = document.getElementById("r1");
let r2 = document.getElementById("r2");
let r3 = document.getElementById("r3");
let r4 = document.getElementById("r4");
let select = document.getElementById("users");

let currentstatus=null;
select.addEventListener('change',function(){
    
r1.checked = true;
    if(this.value=="non"){
        userTable(Reimb,null,null);
    }else{
        userTable(Reimb,currentstatus,this.value);
    }
    
});
r1.addEventListener('click', function() {
    //all
    select.selectedIndex = "non";
    currentstatus = null;
    FilteredTable(Reimb);

    
  });
  r2.addEventListener('click', function() {
      //Approved
      select.selectedIndex = "non";
      currentstatus = "approved";
      FilteredTable(Reimb,"approved");

    
});
r3.addEventListener('click', function() {
    //Denied
    select.selectedIndex = "non";
    currentstatus = "denied";
    FilteredTable(Reimb,"denied");

    
});
r4.addEventListener('click', function() {
    //Pending
    select.selectedIndex = "non";
    currentstatus = "pending";
    FilteredTable(Reimb,"pending")

    
});