//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/library/actor-cocoa-base/build/java/im/actor/model/api/rpc/ResponseGetFileUploadPartUrl.java
//

#ifndef _APResponseGetFileUploadPartUrl_H_
#define _APResponseGetFileUploadPartUrl_H_

#include "J2ObjC_header.h"
#include "im/actor/model/network/parser/Response.h"

@class BSBserValues;
@class BSBserWriter;
@class IOSByteArray;

#define APResponseGetFileUploadPartUrl_HEADER 141

@interface APResponseGetFileUploadPartUrl : APResponse

#pragma mark Public

- (instancetype)init;

- (instancetype)initWithNSString:(NSString *)url;

+ (APResponseGetFileUploadPartUrl *)fromBytesWithByteArray:(IOSByteArray *)data;

- (jint)getHeaderKey;

- (NSString *)getUrl;

- (void)parseWithBSBserValues:(BSBserValues *)values;

- (void)serializeWithBSBserWriter:(BSBserWriter *)writer;

- (NSString *)description;

@end

J2OBJC_EMPTY_STATIC_INIT(APResponseGetFileUploadPartUrl)

J2OBJC_STATIC_FIELD_GETTER(APResponseGetFileUploadPartUrl, HEADER, jint)

FOUNDATION_EXPORT APResponseGetFileUploadPartUrl *APResponseGetFileUploadPartUrl_fromBytesWithByteArray_(IOSByteArray *data);

FOUNDATION_EXPORT void APResponseGetFileUploadPartUrl_initWithNSString_(APResponseGetFileUploadPartUrl *self, NSString *url);

FOUNDATION_EXPORT APResponseGetFileUploadPartUrl *new_APResponseGetFileUploadPartUrl_initWithNSString_(NSString *url) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT void APResponseGetFileUploadPartUrl_init(APResponseGetFileUploadPartUrl *self);

FOUNDATION_EXPORT APResponseGetFileUploadPartUrl *new_APResponseGetFileUploadPartUrl_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(APResponseGetFileUploadPartUrl)

typedef APResponseGetFileUploadPartUrl ImActorModelApiRpcResponseGetFileUploadPartUrl;

#endif // _APResponseGetFileUploadPartUrl_H_
