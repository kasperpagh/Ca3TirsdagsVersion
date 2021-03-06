
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


   var loggedin = false;

    $http({
        method: 'GET',
        url: 'api/demouser'
    }).then(function successCallback(res) {
        $scope.data = res.data.message;
    }, function errorCallback(res) {
        $scope.error = res.status + ": " + res.data.statusText;
        window.location.href = '/SemesterSeed/#/view1';
    });

    $scope.getfromapi = function () {




 
        $http({method: 'GET', url: 'http://cvrapi.dk/api?' + $('#searchbyvalue').val() + '= + ' + $('#searchcriteria').val() + ' + "&country=' + $('#searchbycountry').val(),
            skipAuthorization: true})
                .then(function (response) {
                    $scope.firmName = response.data.name;
                    $scope.firm = response.data;
                    $scope.productionunits = response.data.productionunits;


                    
                });
                
                
      };

    $scope.search = function ()
    {
        $scope.getfromapi();
    };




});

//custom filter som sørger for at den key i obj som er = productionunits ikke kommer med. 
//Dvs obj bliver taget ind, og keys med value lagt i et nyt object.
app.filter('myFilter', function () {

    return function (obj) {
        var a = {};
        angular.forEach(obj, function (value, key) {
            if (key === "vat" || key === "name" || key === "address" || key === "zipcode" || key === "city" ||
                    key === "startdate" || key === "enddate" || key === "employees") {
                a[key] = value;
            }
        });
        return a;


    };
});


