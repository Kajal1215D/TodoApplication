var view = function () {
    var self = this;
    self.firstName = ko.observable("");
    self.lastName = ko.observable("");
    self.gender = ko.observable("");
    self.email = ko.observable("");
    self.dob = ko.observable("");
    self.password = ko.observable("");
    self.regDate = ko.observable();
    self.actStatus = ko.observable();
    self.actDate = ko.observable("");
    self.role = ko.observable();
    self.userList = ko.observableArray();
}

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

var ViewModel = function () {
    ko.validation.rules['text'] = {
        validator: function (val, params) {
            var regex = /([A-Za-z])$/;
            if (regex.test(val)) {
                return true;
            }
        },
        message: 'Only Alphabets Allowed '
    };


    ko.validation.rules['email'] = {
        validator: function (val, params) {
            var regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (regex.test(val)) {
                return true;
            }
        },
        message: 'Enter valid Email id'
    };

    ko.validation.rules['exists'] = {
        validator: function (val, params) {
            ajaxFunction(getAllUserUri, 'GET').done(function (data) {
                self.userList(data);
            });
            let flag = 0;
            for (let i = 0; i < self.userList().length; i++) {
                if (self.userList()[i].email == self.email()) {
                    flag = 1;
                }
            }
            if (flag == 0) {
                return true;
            }
        },
        message: ' Email Already Exits'
    };


    ko.validation.rules['Date'] = {
        validator: function (val, params) {
            var regex = new Date().getFullYear - this.birthDate().getFullYear;
            if (regex > 18) {
                return true;
            }
            else {
                return false;
            }
        },
        message: 'Age must be Greater than 18'
    };

    ko.validation.rules['password'] = {
        validator: function (val, params) {
            var regex=  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
            console.log(val)
            console.log(regex.test(val))
            console.log(val.length >=8)
            if (regex.test(val) && val.length >=8) {
                return true;
            }
        },
        message: 'Password must contain uppercase,lowercase,speacial sign & length should be greater than or eqal to 8 alphabets.'
    };
    ko.validation.registerExtenders();

    var self = this;
    self.firstName = ko.observable().extend({ required: true, text: true });
    self.lastName = ko.observable().extend({ required: true, text: true });
    self.gender = ko.observable();
    self.email = ko.observable().extend({ required: true, email: true, exists: true });
    self.dob = ko.observable().extend({ required: true, dob: true });
    self.password = ko.observable().extend({ required: true, password: true });
    self.regDate = ko.observable(new Date().toLocaleDateString());
    self.actStatus = ko.observable(false);
    self.actDate = ko.observable("")
    self.role = ko.observable('user');
    self.userList = ko.observableArray();

    // Date Conversion to yyyy-mm-dd
    function dateToString(date) {
        dateInString = [
            date.getFullYear(),('0' + (date.getMonth() + 1)).slice(-2),('0' + date.getDate()).slice(-2)
        ].join('-');
        return dateInString;
    }

    // 
    self.addNewUser = function addNewUser(newUser) {
        var newUser = {
            firstName: self.firstName(),
            lastName: self.lastName(),
            gender: self.gender(),
            email: self.email(),
            birthDate: self.dob(),
            password: self.password(),
            regDate: dateToString(new Date()),
            actDate: dateToString(new Date()) ,
            actStatus: self.actStatus(),
            role: self.role()
        };
        console.log(newUser)
        ajaxFunction(getAllUserUri, 'POST', newUser).done(function (data) {
        });
        alert('User Added Successfully !');
        location.reload(true);
    }
};
ko.applyBindings(new ViewModel());