'use strict';

angular.module('growupApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('consume', {
                parent: 'entity',
                url: '/consumes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'growupApp.consume.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/consume/consumes.html',
                        controller: 'ConsumeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('consume');
                        $translatePartialLoader.addPart('status');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('consume.detail', {
                parent: 'entity',
                url: '/consume/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'growupApp.consume.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/consume/consume-detail.html',
                        controller: 'ConsumeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('consume');
                        $translatePartialLoader.addPart('status');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Consume', function($stateParams, Consume) {
                        return Consume.get({id : $stateParams.id});
                    }]
                }
            })
            .state('consume.new', {
                parent: 'consume',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/consume/consume-dialog.html',
                        controller: 'ConsumeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    applyDate: null,
                                    applyValue: null,
                                    status: null,
                                    auditDate: null,
                                    auditValue: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('consume', null, { reload: true });
                    }, function() {
                        $state.go('consume');
                    })
                }]
            })
            .state('consume.edit', {
                parent: 'consume',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/consume/consume-dialog.html',
                        controller: 'ConsumeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Consume', function(Consume) {
                                return Consume.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('consume', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('consume.delete', {
                parent: 'consume',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/consume/consume-delete-dialog.html',
                        controller: 'ConsumeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Consume', function(Consume) {
                                return Consume.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('consume', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
