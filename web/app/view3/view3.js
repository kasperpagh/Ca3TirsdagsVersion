'use strict';

var app = angular.module('myApp.view3', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'app/view3/view3.html',
                });
            }]);



app.controller('View3Ctrl', function ($scope, $http)
{
    $scope.oneAtATime = true;


          $http({
            method: 'GET',
            url: 'api/demouser'
          }).then(function successCallback(res) {
            $scope.data = res.data.message;
          }, function errorCallback(res) {
            $scope.error = res.status + ": "+ res.data.statusText;
          });

    $scope.getfromapi = function () {
        

  

        
        $http({method: 'GET', url: 'http://cvrapi.dk/api?' + $('#searchbyvalue').val() + '= + ' + $('#searchcriteria').val() + ' + "&country=' + $('#searchbycountry').val(),
            skipAuthorization: true})
                .then(function (response) {

                    $scope.productionunits = response.data.productionunits;



                });
    };

    $scope.search = function()
    {
        $scope.getfromapi();
    };



});



