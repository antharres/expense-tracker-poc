(function () {
    angular.module('expense-tracker')
        .controller('HomeController', function ($scope, $http, $log) {
            var self = this;
            $http.get('/resource/').success(function (data) {
                $log.info('HomeController: ' + JSON.stringify(data));
                self.greeting = data;
            })
        })
})();
