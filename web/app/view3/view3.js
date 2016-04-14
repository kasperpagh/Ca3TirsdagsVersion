'use strict';

var app = angular.module('myApp.view3', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view3', {
    templateUrl: 'app/view3/view3.html'
    
  });
}]);
        app.controller('apictrl', function($scope, $http) {
       
        $http({
        method : "GET",
        skipAuthorization: true,
        url : "http://cvrapi.dk/api?vat=3167%208021&country=dk",
//         headers: {
//   
//   'User-Agent' : "CVR API-CA3 SCHOOL Exercise-SEBASTIAN-cph-sr148@cphbusiness.dk"
//   
// }
    }).then(function(response) {
   
        var list = [];
        $scope.members = response.data;
        $scope.productionunits = response.data.productionunits;
        $scope.heads = Object.keys($scope.productionunits);
        $scope.llama = $scope.productionunits.entries();
        
        console.log($scope.llama[1]);
        
        $scope.productionunits2 = function(list)
        {
         list = response.data.productionunits;
         return list;   
        };
        
        /*
        for(var i = 0; i <$scope.members.length; i++)
        {
        $scope.members2 = $scope.members[i];
        } */
    }); 
});