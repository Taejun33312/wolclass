package com.wolclass.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wolclass.domain.ClassVO;
import com.wolclass.domain.PayDTO;
import com.wolclass.domain.RsrvPayVO;
import com.wolclass.domain.SubscriptionVO;
import com.wolclass.domain.TimetableVO;
import com.wolclass.persistance.THDAO;

@Service
public class THServiceImpl implements THService {
	
	

	private static final Logger logger = LoggerFactory.getLogger(THServiceImpl.class);

	@Inject
	private	THDAO dao;
		
	@Override
	public String getTime() {
		
		return dao.getDBTime();
	}
	
	@Override
	public ClassVO getClassDetail(Integer c_no) throws Exception {
		logger.info("service-dao호출");
		return dao.selectClass(c_no);
	}

	@Override
	public List<TimetableVO> getTimetable(Integer c_no) throws Exception {
		List resultVO = dao.getTimetable(c_no);
		return resultVO;
	}

	@Override
	public List<TimetableVO> getTime(TimetableVO vo) throws Exception {
		logger.info("service도착");
		List resultVO = dao.getTime(vo);
		return resultVO;
	}

	@Override
	public TimetableVO getRemainNum(TimetableVO vo) throws Exception {
		logger.info("service도착");
		TimetableVO resultVO = dao.getRemainNum(vo);
		return resultVO;
	}

	@Override
	public SubscriptionVO getSubsInfo(String m_id) throws Exception {
		
		return dao.getSubsInfo(m_id);
	}

	@Override
	public String makeP_no() throws Exception {
		String pno = dao.makeP_no();
		return pno;
	}

	@Override
	public Integer payment(PayDTO pdto) throws Exception {
		logger.info("service도착");
		
		double subs = (double) (pdto.isSubs() ? 0.5 : 1);
		int totalPrice = (int) ((pdto.getC_price()*subs))+(pdto.getC_price()*(pdto.getPeopleNum()-1))-pdto.getPoint();
		pdto.setP_rsrvdate(pdto.getSelectedDate()+" "+pdto.getT_start());
		logger.info("totalPrice"+totalPrice);
		if(totalPrice!=pdto.getPrice()) {
			return null;
		}
		
		Integer cnt = dao.payment(pdto);
		return cnt;
	}
	
	
	
	
}
