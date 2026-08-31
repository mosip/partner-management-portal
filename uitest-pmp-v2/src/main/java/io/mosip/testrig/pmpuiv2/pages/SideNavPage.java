package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The left side navigation panel (MOSIP-38412).
 *
 * The panel is pinned with "fixed top-14 bottom-0", so it should always run
 * from just under the header to the bottom edge of the viewport, whatever the
 * page behind it is doing.
 */
public class SideNavPage extends BasePage {

	/** The panel carries no id of its own, so it is matched on its pinning classes. */
	private static final By SIDE_PANEL = By
			.xpath("//div[contains(@class,'fixed') and contains(@class,'top-14') and contains(@class,'bottom-0')]");

	// The header is h-14 in Tailwind, i.e. 3.5rem / 56px.
	private static final int HEADER_HEIGHT_PX = 56;
	// Allows for sub-pixel rounding on fractional device pixel ratios.
	private static final int TOLERANCE_PX = 4;

	@FindBy(id = "side_nav_home_icon")
	private WebElement sideNavHomeIcon;

	public SideNavPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSidePanelDisplayed() {
		return isDisplayed(SIDE_PANEL);
	}

	/**
	 * True when the panel starts at the header and runs all the way to the bottom
	 * of the viewport.
	 */
	public boolean isSidePanelCoveringFullHeight() {
		WebElement panel = waitAndFindElement(SIDE_PANEL);
		return Boolean.TRUE.equals(((JavascriptExecutor) driver).executeScript(
				"var b=arguments[0].getBoundingClientRect();" + "var header=arguments[1];" + "var tol=arguments[2];"
						+ "return Math.abs(b.top-header)<=tol && Math.abs(b.bottom-window.innerHeight)<=tol;",
				panel, HEADER_HEIGHT_PX, TOLERANCE_PX));
	}

	/** Scrolls the page to the bottom and re-checks that the panel stays pinned. */
	public boolean isSidePanelCoveringFullHeightAfterScroll() {
		scrollToEndPage();
		return isSidePanelCoveringFullHeight();
	}

	public void clickOnSidePanelHomeIcon() {
		clickOnElement(sideNavHomeIcon);
	}
}
