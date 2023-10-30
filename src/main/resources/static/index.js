$(document).ready(function() {
    console.log("here here here here");
    $.ajax({
        url: "/initializeAddressBook"
    }).then(function(data) {
        console.log("here");
        $(".AddressBook").text("Address Book - " + data.id);
    });

    $('.new').on("click", function(e) {
        $.ajax({
            url: "/initializeAddressBook"
        }).then(function(data) {
            console.log("here");
            $('.table-body').remove();
            $('table tbody').after('<tbody class="table-body"></tbody>');
            $(".AddressBook").append(data.id);
            $(".AddressBook").text("Address Book - " + data.id);

        });
    })

    $('.old').on("click", function(e) {
        $.ajax({
            url: "/viewBook"
        }).then(function(data) {
            $(".AddressBook").text("Address Book - " + data.id);
            $('.table-body').remove();
            $('table tbody').after('<tbody class="table-body"></tbody>');
            var html = "";
            for (buddy in data.buddies) {
                html += '<tr> <td class=id-cell>' + data.buddies[buddy].id + "</td> <td>" + data.buddies[buddy].name + '</td>' +
                    '<td>' + data.buddies[buddy].phoneNumber + '</td>' + '<td>' + data.buddies[buddy].address + '</td> </tr>';
            };
            $('#buddiesTable .table-body').html(html);
        });
    })

    $("form#addBudForm").submit(function(e) {
        e.preventDefault();

        var $inputs = $("#addBudForm :input");

        var values = [];
        $inputs.each(function() {
            console.log($(this).val());
            values.push($(this).val());
        });
        var t = $(".AddressBook").text()
        var id = t.charAt(t.length - 1);

        $.ajax({
            url: "/addBuddyToBook",
            type: "post",
            dataType: "json",
            data: {
                "id": id,
                "name": values[0],
                "number": values[1],
                "address": values[2]
            },
            success: function(data) {
                html = $('#buddiesTable .table-body').html() + '<tr> <td class=id-cell>' + data.id
                + "</td> <td>" + data.name + '</td>' + '<td>' + data.phoneNumber + '</td>'
                + '<td>' + data.address + '</td> </tr>';
                $('#buddiesTable .table-body').html(html);
            }
        });
    });
});