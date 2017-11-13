package com.github.raidom.app;

//import com.github.raidom.app.domain.node1.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import javax.servlet.http.*;

@RestController
@RequestMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockResource {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "Ping";
    }
	@Autowired
	private Environment environment;

	@RequestMapping(value = "/get-env", method = RequestMethod.GET)
	public String getEnv(@RequestParam String env) {
		return "Env" + " " + environment.getProperty(env);
	}

	@Autowired
		private StockItemRepository stockItemRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
		public StockItem stockItem(@PathVariable("id") Long id, HttpServletResponse response) {

    StockItem stockItem = stockItemRepository.findOne(id);

    if (stockItem == null) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return null;
    }

    return stockItem;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
		public StockItem storeInStock(@RequestBody StockItem stockItem, HttpServletResponse response) {
		StockItem storedItem = stockItemRepository.save(stockItem);

		response.setStatus(HttpStatus.CREATED.value());

    return storedItem;
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
		public StockItem update(@RequestBody StockItem stockItem, HttpServletResponse response) {
			if (!stockItemRepository.exists(stockItem.getId())) {
				response.setStatus(HttpStatus.NOT_FOUND.value());
			return stockItem;
			}

		return stockItemRepository.save(stockItem);
		}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public void removeFromStock(@PathVariable("id") Long id, HttpServletResponse response) {
			StockItem stockItemToRemove = stockItemRepository.findOne(id);

			if (stockItemToRemove == null) {
				response.setStatus(HttpStatus.NOT_FOUND.value());
				return;
			}

			stockItemRepository.delete(stockItemToRemove);
		}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<StockItem> items() {
		return stockItemRepository.findAll();
	}
	

}