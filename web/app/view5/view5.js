'use strict';

var app = angular.module('myApp.view5', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view5', {
                    templateUrl: 'app/view5/view5.html'
                });
            }]);



app.controller('View5Ctrl', function ($scope, $http)
{

    $http({
        method: 'GET',
        url: 'api/admin/users'
    }).then(function (response) {
        $scope.usersForList = [];
        $scope.usersForList = response.data;

    });

    $scope.delUsr = function (userName)
    {
        $http({
            method: 'DELETE',
            url: 'api/admin/user/' + userName
        }).then(function (response) {

            location.reload(true);
        });
    };


});

