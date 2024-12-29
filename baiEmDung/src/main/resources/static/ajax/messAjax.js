function getMess() {
    $.ajax({
        url: "/mess-api/mess-user",
        type: "GET",
        success: function (response) {
            var table = $('#table-mess');
            table.empty();
            response.forEach(function(mess, index) {
                var checkMess = mess[5];
                var box = '';
                if(checkMess == 'sent') {
                    box = `
                <div class="message message-a">
                    ${mess[3]}
                    <div class="message-time">${mess[4]}</div>
                </div>`
                }else {
                    box = `
                     <div class="message message-b">
                        ${mess[3]}
                        <div class="message-time">${mess[4]}</div>
                    </div>`
                }
                table.append(`
                    ${box}
                `);
            })
            console.log(response)
        },
        error: function (xhr) {
            console.log("loi: " + xhr.responseText)
        }

    })

}

function filterMess() {
    $.ajax({
        type: "POST",
        url:"/mess-api/fillter",
        contentType: "application/json", // Chuyển thành JSON
        data: JSON.stringify({
            start: $('#start_date').val().trim(),
            end: $('#end_date').val().trim()
        }),
        success: function (response) {
            getMess();
        },
        error: function (xhr) {
            console.error('loi phan trang cho bill deatil' + xhr.responseText)
        }
    })
}


$(document).ready(function () {
    // getMess();
    const today = new Date();
    const startDate = new Date(today); // Ngày bắt đầu
    const endDate = new Date(today); // Ngày kết thúc

    // Cộng thêm 7 ngày cho ngày kết thúc
    endDate.setDate(today.getDate() - 7);

    // Thiết lập giá trị mặc định cho start_date và end_date
    $('#end_date').val(startDate.toISOString().split('T')[0]); // Gán ngày bắt đầu
    $('#start_date').val(endDate.toISOString().split('T')[0]); // Gán ngày kết thúc
    filterMess()
});