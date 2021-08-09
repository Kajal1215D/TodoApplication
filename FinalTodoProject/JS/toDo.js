var ViewModel = function () {
    var self = this;
    self.todoName = ko.observable();
    self.description = ko.observable();
    self.startDate = ko.observable();
    self.completionDate = ko.observable();
    self.priority = ko.observable('low');
    self.toDoStatus = ko.observable(false);
    self.currentUserObj = ko.observable(JSON.parse(sessionStorage.getItem('currentUserObj')));
    self.allTodoList = ko.observableArray();

    console.log(self.currentUserObj())
    console.log(self.currentUserObj().user_id)
    // Common Uri
    var getAllTodosUri = 'http://localhost:8080/FinalTodoApplication/todoapplication/users/' + 2 + '/todos';

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
            date.getFullYear(), ('0' + (date.getMonth() + 1)).slice(-2), ('0' + date.getDate()).slice(-2)
        ].join('-');
        return dateInString;
    }

    //Get Todo List
    function getAllTodos() {
        ajaxFunction(getAllTodosUri, 'GET').done(function (data) {
            console.log(self.currentUserObj().user_id)
            self.allTodoList(data);
        });
    }

    // AddNewTodo
    self.addNewTodo = function () {
        console.log("In addd")
        var newTodoObj = {
            todoName: self.todoName(),
            description: self.description(),
            startDate: dateToString(new Date()),
            completionDate: self.completionDate(),
            priority: self.priority(),
            toDoStatus: self.toDoStatus()
        }
        console.log(newTodoObj)
        var addTodoUri = getAllTodosUri;
        ajaxFunction(addTodoUri, 'POST', newTodoObj).done(function (data) {
            getAllTodos();
            location.reload(true);
        });
        
    }

    // DeleteTodo
    self.deleteTask = function (deleteTodo) {
        console.log(deleteTodo.todo_id)
        var deletetTodoUri = getAllTodosUri + '/' + deleteTodo.todo_id;
        ajaxFunction(deletetTodoUri, 'DELETE').done(function () {
            getAllTodos();
        });
        location.reload(true);
    }

    // ChangeTodoStatus
    self.changeStatus = function (updatedTodo) {
        var updateTodoUri = getAllTodosUri + '/' + updatedTodo.todo_id;
        ajaxFunction(updateTodoUri, 'PUT', updatedTodo).done(function () {
            getAllTodos();
        });
        location.reload(true);
    }

    // EditTask
    self.editTask = function (updatedTodo) {
        self.todoName(updatedTodo.todoName);
        self.description(updatedTodo.description);
        self.startDate(updatedTodo.startDate);
        self.completionDate(updatedTodo.completionDate);
        self.priority(updatedTodo.priority);

        $('#Save').hide();
        $('#Update').show();

        self.updateTask = function () {
            var newTodoObj = {
                todoName: self.todoName(),
                description: self.description(),
                startDate: self.startDate(),
                completionDate: self.completionDate(),
                priority: self.priority(),
                toDoStatus: self.toDoStatus()
            };
            var updateTodoUri = getAllTodosUri + '/' + updatedTodo.todo_id;
            ajaxFunction(updateTodoUri, 'PUT', newTodoObj).done(function () {
                getAllTodos();
                alert("Update succesfull")
            });
            location.reload(true);
            $('#Save').show();
            $('#Update').hide();
        }
    }
    getAllTodos();
}
ko.applyBindings(new ViewModel());
