function exchangePassWord() {
    $.ajax({
        type: "POST",
        url:"/mess-api/exchange-password",
        contentType: "application/json", // Chuyển thành JSON
        data: JSON.stringify({
            passWordRoot: $('#passWordRoot').val().trim(),
            passWordNew: $('#passWordNew').val().trim(),
            passWordNewConfirm: $('#passWordNewConfirm').val().trim()
        }),
        success: function (response) {
            createToast(response.check, response.message);

        },
        error: function (xhr) {
            console.error('loi phan trang cho bill deatil' + xhr.responseText)
        }
    })
}
$(document).ready(function () {

});