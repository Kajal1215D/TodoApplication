var viewModel = function () {
    var self = this;
    self.userName = ko.observable();
    self.password = ko.observable();
    self.actionForm = ko.observable();
    self.userList = ko.observableArray();

    // Common Uri
    var getAllUserUri = 'http://localhost:8080/FinalTodoApplication/todoapplication/users';

    //Ajax Function
    function ajaxFunction(uri, method, data) {
        return $.ajax({
            type: method,
            url: uri,
            dataType: 'json',
            contentType: 'application/json',
            data: data ? JSON.stringify(data) : null
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert('Error : ' + errorThrown);
        });
    }

    //Get User List
    function getAllUser() {
        ajaxFunction(getAllUserUri, 'GET').done(function (data) {
            self.userList(data);
        });
    }

    self.userLogin = function () {
        var flag = 0;
        console.log(self.userList())
        for (let i = 0; i < self.userList().length; i++) {
            console.log(self.userList()[i].email === self.userName());
            if (self.userList()[i].email === self.userName() && self.userList()[i].password === self.password() && self.userList()[i].role === "user" && self.userList()[i].actStatus === true) {
                console.log("User present")
                var currentUserObj = self.userList()[i];
                self.actionForm("toDo.html");
                flag = 1;
                break;
            }

            else if (self.userList()[i].email === self.userName() && self.userList()[i].password === self.password() && self.userList()[i].role === "admin" && self.userList()[i].actStatus === true) {
                var currentUserObj = self.userList()[i];
                self.actionForm('adminPage.html');
                console.log("Admin present")
                flag = 1;
                break;

            }
        }
        if (flag == 1) {
            sessionStorage.setItem("currentUserObj", JSON.stringify(currentUserObj));
            alert("Login Successful");
            return true;
        }
        else {
            alert("Invalid Credential or Account Deactivated");
            return false;
        }
    }
    getAllUser();
}
ko.applyBindings(new viewModel());