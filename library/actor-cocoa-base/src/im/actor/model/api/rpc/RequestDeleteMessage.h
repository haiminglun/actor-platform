//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/library/actor-cocoa-base/build/java/im/actor/model/api/rpc/RequestDeleteMessage.java
//

#ifndef _APRequestDeleteMessage_H_
#define _APRequestDeleteMessage_H_

#include "J2ObjC_header.h"
#include "im/actor/model/network/parser/Request.h"

@class APOutPeer;
@class BSBserValues;
@class BSBserWriter;
@class IOSByteArray;
@protocol JavaUtilList;

#define APRequestDeleteMessage_HEADER 98

@interface APRequestDeleteMessage : APRequest

#pragma mark Public

- (instancetype)init;

- (instancetype)initWithAPOutPeer:(APOutPeer *)peer
                 withJavaUtilList:(id<JavaUtilList>)rids;

+ (APRequestDeleteMessage *)fromBytesWithByteArray:(IOSByteArray *)data;

- (jint)getHeaderKey;

- (APOutPeer *)getPeer;

- (id<JavaUtilList>)getRids;

- (void)parseWithBSBserValues:(BSBserValues *)values;

- (void)serializeWithBSBserWriter:(BSBserWriter *)writer;

- (NSString *)description;

@end

J2OBJC_EMPTY_STATIC_INIT(APRequestDeleteMessage)

J2OBJC_STATIC_FIELD_GETTER(APRequestDeleteMessage, HEADER, jint)

FOUNDATION_EXPORT APRequestDeleteMessage *APRequestDeleteMessage_fromBytesWithByteArray_(IOSByteArray *data);

FOUNDATION_EXPORT void APRequestDeleteMessage_initWithAPOutPeer_withJavaUtilList_(APRequestDeleteMessage *self, APOutPeer *peer, id<JavaUtilList> rids);

FOUNDATION_EXPORT APRequestDeleteMessage *new_APRequestDeleteMessage_initWithAPOutPeer_withJavaUtilList_(APOutPeer *peer, id<JavaUtilList> rids) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT void APRequestDeleteMessage_init(APRequestDeleteMessage *self);

FOUNDATION_EXPORT APRequestDeleteMessage *new_APRequestDeleteMessage_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(APRequestDeleteMessage)

typedef APRequestDeleteMessage ImActorModelApiRpcRequestDeleteMessage;

#endif // _APRequestDeleteMessage_H_
