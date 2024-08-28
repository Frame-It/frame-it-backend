package com.org.framelt.portfolio.adapter.out

import com.org.framelt.portfolio.application.port.out.PortfolioCommendPort
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import org.springframework.stereotype.Repository

@Repository
class PortfolioRepository : PortfolioCommendPort , PortfolioReadPort {

}
