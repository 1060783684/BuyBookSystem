/**
 * Created by wangwei on 18-4-29.
 */
$(document).ready(function () {
    //获取当前用户的订单列表
    $.post("/user/getAddressList.do", {}, function (data, status) {
        if (data != undefined) {
            var json = eval("(" + data + ")");
            if(json != undefined) {
                if (json.sresult == RESULT_SUCCESS) {
                    var addressList = json.addressList;
                    var addressInfos = "";
                    for (var i = 0; i < addressList.length; i++) {
                        var addressInfo = "<tr>";
                        var address = addressList[i];
                        if (address.name != undefined) {
                            addressInfo += '<td height="18" bgcolor="#FFFFFF" class="z">' + address.name + '</td>';
                        } else {
                            addressInfo += '<td height="18" bgcolor="#FFFFFF" class="z">' + 无 + '</td>';
                        }
                        if (address.addr != undefined) {
                            addressInfo += '<td height="18" bgcolor="#FFFFFF"><span style="border: 1px solid #E9E9E9; font-size: 14px;">' + address.addr + '</span></td>';
                        } else {
                            addressInfo += '<td height="18" bgcolor="#FFFFFF"><span style="border: 1px solid #E9E9E9; font-size: 14px;">' + 无 + '</span></td>';
                        }
                        if (address.mail != undefined) {
                            addressInfo += '<td height="18" bgcolor="#FFFFFF"><span class="z">' + address.mail + '</span></td>';
                        } else {
                            addressInfo += '<td height="18" bgcolor="#FFFFFF"><span class="z">' + 无 + '</span></td>';

                        }
                        if (address.phone != undefined) {
                            addressInfo += '<td height="18" bgcolor="#FFFFFF"><span class="z">' + address.phone + '</span></td>';
                        } else {
                            addressInfo += '<td height="18" bgcolor="#FFFFFF"><span class="z">' + 无 + '</span></td>';
                        }
                        var addrId = address.id;
                        addressInfo += '<td height="37" bgcolor="#FFFFFF"><span style="border:1px solid #E9E9E9;"><input type="button" value="删除" style="background:red;height:30px" class="tj" color="grey" onclick="delAddress(\''+addrId+'\')"/>'+'</span></td>';
                        addressInfo += "</tr>";
                        addressInfos += addressInfo;
                    }
                    var addressTableHtml = $("#addressTable").html();
                    addressTableHtml += addressInfos;
                    $("#addressTable").html(addressTableHtml);
                }
            }
        }
    });
});
function addAddress() {
    var addr = $("#addr").val();
    var mail = $("#mail").val();
    var name = $("#name").val();
    var phone = $("#phone").val();
    $.post("/user/addAddressInfo.do",{"address":addr,"name":name,"mail":mail,"phone":phone},function (data, status) {
        if (data != undefined) {
            var json = eval("(" + data + ")");
            switch (json.sresult) {
                case RESULT_SUCCESS: {
                    alert("添加成功");
                    window.location.reload();
                    break;
                }
                case RESULT_FAIL: {
                    alert("操作错误！");
                    break;
                }
                case ADDRESS_TOMUCH:{
                    alert("可添加的地址数最多只能有8个！");
                }
                default:
                    break;
            }
        }
    });
}

function delAddress(id) {// id是地址的id
    if(confirm("您确定要删除么？") == true){
        $.post("/user/deleteAddressInfo.do",{"addressId":id},function (data, status) {
            if(data != undefined){
                var json = eval("(" + data + ")");
                if(json != undefined){
                    switch (json.sresult){
                        case RESULT_SUCCESS: {
                            alert("删除成功");
                            window.location.reload();
                            break;
                        }
                        case RESULT_FAIL: {
                            alert("操作错误！");
                            break;
                        }
                    }
                }
            }
        });
    }
}