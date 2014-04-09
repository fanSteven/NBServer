$(function(){
    $("#do_login").bind("click",function(){
        var  name = $("#user_name").val();
        var password = $("#user_pasword").val();
		if(name.trim()==""||password.trim()==""){
			alert("填写必要信息");
			return;
		}
        var login_DataSource = new kendo.data.DataSource({
            transport:{
                create:{
                    url:"http://localhost:8080/NB/login",
                    type:"POST",
                    contentType : "application/json; charset=UTF-8",
                    dataType:"json",
                    data:{
                        "name": name,
                        "password":password
                    },
                    success:function(result){
                       if(result.id != -1){
                           window.location.href = "index.html"
                       }else{
                           alert(result.log);
                        }

                    }
                },
                parameterMap : function(options, operation) {
                    if (operation != "read") {
                        return JSON.stringify(options);
                    }
                    return options;   VirtualMachinePool
                }
            }
        });
        login_DataSource.transport.create();
    })
})

function to_regist() {
    window.location.href = "register.html";
}