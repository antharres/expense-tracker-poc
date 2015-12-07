(function () {
    angular.module('expense-tracker')
        .controller('AccountController', function ($scope, $http, $log) {
            var self = this;
            this.account = {};

            this.entryTypes = [
                {name: 'Expense', value: 'EXPENSE'},
                {name: 'Income', value: 'INCOME'}
            ];

            this.newEntry = {};
            this.errorMessage = '';
            this.successMessage = '';

            this.addNewEntry = function () {
                $http.post("/accounts/entry/",
                    {
                        date: self.newEntry.date,
                        type: self.newEntry.type.value,
                        amount: self.newEntry.amount
                    }).success(function (data) {
                    self.successMessage = data.message;
                    self.refreshAccountData();
                    self.newEntry = {};
                }).error(function (data) {
                    self.clearMessages();
                    self.errorMessage = data.message;
                });
            };

            this.clearMessages = function () {
                self.newEntry = {date: new Date()};
                self.successMessage = '';
                self.errorMessage = '';
            };

            this.refreshAccountData = function () {
                $http.get("/accounts/current/", {})
                    .success(function (data) {
                        self.account = data.value;
                    });
            };

            this.refreshAccountData();

            this.balanceInGreen = function () {
                return self.account.balance >= 100.00;
            };

            this.balanceInWarining = function () {
                return self.account.balance < 100.00 && self.account.balance > -100;
            };

            this.balanceInDanger = function () {
                return self.account.balance <= -100.00;
            };
        })
})();
