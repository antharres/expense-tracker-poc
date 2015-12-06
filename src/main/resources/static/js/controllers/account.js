(function () {
    angular.module('expense-tracker')
        .controller('AccountController', function ($scope, $http, $log) {
            var self = this;
            this.account = {
                //balance: 0, entries: [
                //    {'date': 10000000000, 'type': 'Expense', 'amount': 100.00},
                //    {'date': 21000000000, 'type': 'Expense', 'amount': 1010.00}
                //]
            };

            $http.get("/accounts/current/", {})
                .success(function (data) {
                    self.account = data.value;
                });

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
