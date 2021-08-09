var ViewModel = function() {
    var self = this;
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

    // Date Conversion to yyyy-mm-dd
    function dateToString(date) {
        dateInString = [
            date.getFullYear(),('0' + (date.getMonth() + 1)).slice(-2),('0' + date.getDate()).slice(-2)
        ].join('-');
        return dateInString;
    }

    //Get User List
    function getAllUser() {
        ajaxFunction(getAllUserUri, 'GET').done(function (data) {
            self.userList(data);
            console.log(self.userList())
        });
        console.log(self.userList())
    }

    // Change user Activation Status
    self.changeStatus = function(updateUser){
        if(updateUser.actStatus){
            updateUser.actStatus = false;
        }  
        else{
            updateUser.actStatus = true;
            updateUser.actDate = dateToString(new Date());
        }
        //updateUserUri
        var updateUserUri = getAllUserUri+'/'+updateUser.user_id;  
        ajaxFunction(updateUserUri, 'PUT',updateUser).done(function (data) {
            self.userList(data);
        });
        location.reload(true);
        getAllUser(); 
    }

    // Delete User from List
    self.deleteUser = function(deleteUser){
        console.log("In delete ")
        var deleteUserUri = getAllUserUri+'/'+ 4 ;  
        ajaxFunction(deleteUserUri, 'DELETE').done(function () {
        });
        // location.reload(true);
        getAllUser(); 
    }
    getAllUser(); 
};
ko.applyBindings(new ViewModel());
