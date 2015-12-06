(function () {
    angular.module('expense-tracker')
        .controller('AuthenticationController', function ($rootScope, $http, $location) {
            var self = this;
            this.credentials = {};
            this.authenticatedUser = {};
            $rootScope.authenticated = false;
            this.errorMessage = '';
            this.successMessage = '';

            var clearMessages = function () {
                self.errorMessage = '';
                self.successMessage = '';
            };

            var authenticate = function (credentials, successCallback, failureCallback) {
                var headers = credentials ? {
                    authorization: "Basic "
                    + btoa(credentials.username + ":" + credentials.password)
                } : {};

                $http.get('/users/current/', {headers: headers}).success(function (data) {
                    $rootScope.authenticated = true;
                    self.authenticatedUser = {
                        name: data.value.name
                    };
                    successCallback && successCallback(data);
                }).error(function (data) {
                    $rootScope.authenticated = false;
                    failureCallback && failureCallback(data);
                });

            };

            authenticate();

            this.loginUser = function () {
                clearMessages();
                authenticate(
                    this.credentials,
                    function (data) {
                        $location.path("/");
                    },
                    function (data) {
                        self.errorMessage = data.message || 'Invalid credentials';
                    }
                );
            };

            this.logoutUser = function () {
                self.authenticatedUser = {};
                self.credentials = {};
                clearMessages();
                $http.post('logout', {}).success(function () {
                    $rootScope.authenticated = false;
                    $location.path("/");
                }).error(function () {
                    $rootScope.authenticated = false;
                });
            };

            this.registerUser = function () {
                clearMessages();
                $http.post('/users/', self.credentials).success(function (data) {
                    $location.path("/");
                    self.successMessage = data.message;
                }).error(function (data) {
                    self.errorMessage = data.message;
                });
            };

        })
})();
