'use strict';

angular.module('growupApp')
    .controller('ConsumeController', function ($scope, $state, Consume, ConsumeSearch, ParseLinks) {

        $scope.consumes = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            Consume.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.consumes.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.consumes = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ConsumeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.consumes = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.consume = {
                name: null,
                description: null,
                applyDate: null,
                applyValue: null,
                status: null,
                auditDate: null,
                auditValue: null,
                id: null
            };
        };
    });
