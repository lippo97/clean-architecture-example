package org.example

import org.example.gateway.GoalsGateway
import org.example.gateway.TheoriesGateway

class Dependencies (
    theoriesGateway: TheoriesGateway,
//    goalsGateway: GoalsGateway,
) : TheoriesGateway by theoriesGateway
//    GoalsGateway by goalsGateway