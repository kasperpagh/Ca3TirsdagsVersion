'use strict';

var app = angular.module('myApp.view5', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view5', {
    templateUrl: 'app/view5/view5.html'
  });
}]);
        app.controller('apictrl', function($scope, $http) {
       
        $http({
        method : "GET",
        skipAuthorization: true,
        url : "http://cvrapi.dk/api?vat=3167%208021&country=dk",
         headers: {
   
   'User-Agent' : "CVR API-CA3 SCHOOL Exercise-SEBASTIAN-cph-sr148@cphbusiness.dk"
   
 }
    }).then(function(response) {
   
    
        $scope.members = response.data;
        $scope.productionunits = response.data.productionunits;
        $scope.heads = Object.keys($scope.members);
        
        /*
        for(var i = 0; i <$scope.members.length; i++)
        {
        $scope.members2 = $scope.members[i];
        } */
    }); 
});